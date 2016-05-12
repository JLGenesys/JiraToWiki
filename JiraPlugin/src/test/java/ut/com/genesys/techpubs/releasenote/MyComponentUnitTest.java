package ut.com.genesys.techpubs.releasenote;

import org.junit.Test;
import com.genesys.techpubs.releasenote.api.MyPluginComponent;
import com.genesys.techpubs.releasenote.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}