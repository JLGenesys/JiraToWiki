package com.genesys.techpubs.jira.search;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueFactory;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchProvider;
import com.atlassian.jira.issue.search.SearchProviderFactory;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.plugin.searchrequestview.AbstractSearchRequestView;
import com.atlassian.jira.plugin.searchrequestview.SearchRequestParams;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.util.VelocityParamFactory;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.jira.component.ComponentAccessor;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.lucene.search.IndexSearcher;

@Named ("ReleaseNoteView")
public class ReleaseNoteView extends AbstractSearchRequestView {

	@ComponentImport
    private final JiraAuthenticationContext authenticationContext;
	@ComponentImport
    private final SearchProviderFactory searchProviderFactory;
	@ComponentImport
	private final IssueFactory issueFactory;
	@ComponentImport
	private final SearchProvider searchProvider;
    
    @Inject
    public ReleaseNoteView(JiraAuthenticationContext authenticationContext, SearchProviderFactory searchProviderFactory,
            IssueFactory issueFactory, SearchProvider searchProvider)
    {
        this.authenticationContext = authenticationContext;
        this.searchProviderFactory = searchProviderFactory;
        this.issueFactory = issueFactory;
        this.searchProvider = searchProvider;
       
    }
    
    
    public void writeSearchResults(final SearchRequest searchRequest, final SearchRequestParams searchRequestParams, final Writer writer)
    {
    	
    	
    	//final Map defaultParams = JiraVelocityUtils.getDefaultVelocityParams(authenticationContext);
        
    	final VelocityParamFactory velocitydefaultParams = ComponentAccessor.getVelocityParamFactory();
    	final Map defaultParams = velocitydefaultParams.getDefaultVelocityParams(authenticationContext);
    	
    	try
        {
           
            
    		  final Map headerParams = new HashMap(defaultParams);
    		  final Map issues = new HashMap();
            
          
            @SuppressWarnings("deprecation")
			final IndexSearcher searcher = searchProviderFactory.getSearcher(SearchProviderFactory.ISSUE_INDEX);
            
            final Map<String, Object> issueParams = new HashMap(defaultParams);
            
            //This hit collector is responsible for writing out each issue as it is encountered in the search results.
            //It will be called for each search result by the underlying Lucene search code.
        	@SuppressWarnings("rawtypes")
    		final PagerFilter p = searchRequestParams.getPagerFilter();         
            
            //now run the search that's defined in the issue navigator and pass in the hitcollector from above which will
            //write out each issue in the format specified in this plugin.
          final SearchResults results =  searchProvider.search(searchRequest.getQuery(), authenticationContext.getUser(), p);
 
          	final List<Issue> issuesList = results.getIssues();
            
          	///iterate over list
          	
          	for (int i = 0; i < issuesList.size(); i++) {
    			
          		final Issue currentissue = issuesList.get(i);
          		
          		 issues.put(currentissue.getKey(), currentissue);
    		}
           
           issueParams.put("issues", issues);
           writer.write(descriptor.getHtml("header", headerParams));
           writer.write(descriptor.getHtml("singleissue", issueParams));
         //finally lets write the footer.
           writer.write(descriptor.getHtml("footer"));

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (SearchException e)
        {
           throw new RuntimeException(e);
        }
    }
}
