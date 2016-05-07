<?php
class SpecialJiraToWiki extends SpecialPage {
	function __construct() {
		parent::__construct( 'JiraToWiki' );
	}

	function execute( $par ) {
		$request = $this->getRequest();
		$output = $this->getOutput();
		$this->setHeaders();

		# Get request data from, e.g.
		$param = $request->getText( 'param' );

		# Do stuff
		# ...
	//	$wikitext = 'Hello world!';
	//	$output->addWikiText( $wikitext );
	//	$output->addWikiText($param);

		//$output->addHTML("<h2>POST</h2>");
		
		//$str_post = json_encode($_POST);

		//$output->addHTML("<pre>".$str_post."</pre>");

		//$output->addHTML("<h2>Loop Test</h2>");
		
		$newStr = "";
		$correctionStr = "";
		$knownStr= "";

		foreach ($_POST as $key => $value) {
    	//$output->addHTML( "<p>Key: $key; Value: $value</p><br />\n");

			//$output->addHTML("<p>key value: $key : $value</p>");
			$pos = strripos($key, "issueKey-");

			//$output->addHTML("<p>Pos: $pos</p>");

			if($pos === 0){

				$issuetypekey=  "issueType-".$value;
				$issuefieldkey=  "issueField-".$value;
				//$output->addHTML("<p>issuefieldkey: $issuefieldkey</p>");
				//$output->addHTML("<p> Text:".$_POST["issueField-XEB-4="]."</p>");

				switch ( $_POST[$issuetypekey] ) {
					case 'new':
						$newStr .= "* ".$_POST[$issuefieldkey]."\n";
					//	$output->addHTML("<p>New Str: $newStr</p>");
						break;
					
					case 'correction':
						$correctionStr .= "* ".$_POST[$issuefieldkey]."\n";
					//	$output->addHTML("<p>Corr Str: $correctionStr</p>");
						//$output->addHTML("<p> Field:".$_POST[$issuefieldkey]."</p>");
						break;

					case 'known':
						$knownStr .= "* ".$_POST[$issuefieldkey]."\n";
					//	$output->addHTML("<p>knownStr: $knownStr</p>");
						break;
							
					default:
						# code...
						break;
				}

			}

			}



		$rnversion = $_POST["rnversion"];
		$rnproduct = $_POST["rnproduct"];
		//$releasePage = new Article( Title::newFromText( "Documentation:RN:".$component."85rn:".$component.$releaseversionfamily.":8.5.x" ));

		$releasePage = new Article( Title::newFromText( "$rnproduct Release Note $rnversion" ));

		//$releaseText = $releaseTemplate->getContent();

$releaseText = "==New==
$newStr

==Corrections==
$correctionStr

==Known==

$knownStr";

		
		$releasePage->doEdit($releaseText,"RN Automation Edit.");
		//$releasePage->getPage()->doPurge();

		$output->addWikiText("<p>Created [[$rnproduct Release Note $rnversion]]</p>");
		




	}
}