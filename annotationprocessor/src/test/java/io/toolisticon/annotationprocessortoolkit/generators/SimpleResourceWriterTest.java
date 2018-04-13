package io.toolisticon.annotationprocessortoolkit.generators;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for {@link SimpleResourceWriter}.
 */
public class SimpleResourceWriterTest {

    private SimpleResourceWriter unit;

    private FileObject fileObject;

    private StringWriter stringWriter;

    @Before
    public void init() throws IOException {

        fileObject = Mockito.mock(FileObject.class);
        stringWriter = Mockito.spy(new StringWriter());
        Mockito.when(fileObject.openWriter()).thenReturn(stringWriter);


        unit = new SimpleResourceWriter(fileObject);
    }

    @Test
    public void testAppend() throws IOException {

        unit.append("ABC");
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABC"));
        Mockito.verify(stringWriter, Mockito.times(1)).flush();

        unit.append("DEF");
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABCDEF"));
        Mockito.verify(stringWriter, Mockito.times(2)).flush();

    }

    @Test
    public void testWrite_strings() throws IOException {

        unit.write("ABC");
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABC"));
        Mockito.verify(stringWriter, Mockito.times(1)).flush();

        unit.write("DEF");
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABCDEF"));
        Mockito.verify(stringWriter, Mockito.times(2)).flush();

    }

    @Test
    public void testWrite_charArrays() throws IOException {

        unit.write("ABC".toCharArray());
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABC"));
        Mockito.verify(stringWriter, Mockito.times(1)).flush();

        unit.write("DEF".toCharArray());
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABCDEF"));
        Mockito.verify(stringWriter, Mockito.times(2)).flush();

    }

    @Test
    public void testWrite_template() throws IOException {

        final String TEMPLATE = "/testcases/generators/testTemplate.tpl";
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("value","CD");

        unit.writeTemplate(TEMPLATE,model);
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABCDEF"));
        Mockito.verify(stringWriter, Mockito.times(1)).flush();


    }

    @Test
    public void testClose() throws IOException {

        unit.write("ABC");
        MatcherAssert.assertThat(stringWriter.getBuffer().toString(), Matchers.is("ABC"));
        Mockito.verify(stringWriter, Mockito.times(1)).flush();

        unit.close();

        Mockito.verify(stringWriter, Mockito.times(1)).close();


    }

}
