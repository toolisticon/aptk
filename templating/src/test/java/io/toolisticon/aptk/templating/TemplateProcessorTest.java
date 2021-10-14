package io.toolisticon.aptk.templating;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for {@link TemplateProcessor}.
 */
public class TemplateProcessorTest {

    @Test
    public void testTemplateString() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "YEP");

        MatcherAssert.assertThat(TemplateProcessor.processTemplate("${test}", map), Matchers.is("YEP"));


    }

    @Test
    public void testTemplateFile() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "YEP");

        MatcherAssert.assertThat(TemplateProcessor.processTemplateResourceFile("/TestTemplateProcessorTemplateFile.tpl", map), Matchers.is("YEP"));


    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemplateFile_withNonExistingFile() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "YEP");

        TemplateProcessor.processTemplateResourceFile("/XXX.tpl", map);


    }
}
