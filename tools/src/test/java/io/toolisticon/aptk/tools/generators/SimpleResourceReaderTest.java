package io.toolisticon.aptk.tools.generators;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * Unit test for {@link SimpleJavaWriter}.
 */
public class SimpleResourceReaderTest {

    @Test
    public void testReadln() throws IOException {

        FileObject fileObject = Mockito.mock(FileObject.class);
        StringReader stringReader = Mockito.spy(new StringReader("abc\ndef\nhij"));
        Mockito.when(fileObject.openReader(Mockito.anyBoolean())).thenReturn(stringReader);


        SimpleResourceReader unit = new SimpleResourceReader(fileObject);

        MatcherAssert.assertThat(unit.readLine(), Matchers.equalTo("abc"));
        MatcherAssert.assertThat(unit.readLine(), Matchers.equalTo("def"));
        MatcherAssert.assertThat(unit.readLine(), Matchers.equalTo("hij"));
        MatcherAssert.assertThat(unit.readLine(), Matchers.nullValue());

        unit.close();

        Mockito.verify(stringReader, Mockito.times(1)).close();

    }

    @Test
    public void testReadAsString() throws IOException {

        final String RESOURCE_STRING = "abc\ndef\nhij";

        FileObject fileObject = Mockito.mock(FileObject.class);
        StringReader stringReader = Mockito.spy(new StringReader(RESOURCE_STRING));
        Mockito.when(fileObject.openReader(Mockito.anyBoolean())).thenReturn(stringReader);


        SimpleResourceReader unit = new SimpleResourceReader(fileObject);

        MatcherAssert.assertThat(unit.readAsString(), Matchers.equalTo(RESOURCE_STRING));
        Mockito.verify(stringReader, Mockito.times(1)).close();

    }

    @Test
    public void testReadAsLines() throws IOException {

        final String RESOURCE_STRING = "abc\ndef\nhij";

        FileObject fileObject = Mockito.mock(FileObject.class);
        StringReader stringReader = Mockito.spy(new StringReader(RESOURCE_STRING));
        Mockito.when(fileObject.openReader(Mockito.anyBoolean())).thenReturn(stringReader);


        SimpleResourceReader unit = new SimpleResourceReader(fileObject);

        MatcherAssert.assertThat(unit.readAsLines(), Matchers.contains("abc", "def", "hij"));
        Mockito.verify(stringReader, Mockito.times(1)).close();

    }

    @Test
    public void testReadAsLines_withTrim() throws IOException {

        final String RESOURCE_STRING = "abc \n  def\n  hij   ";

        FileObject fileObject = Mockito.mock(FileObject.class);
        StringReader stringReader = Mockito.spy(new StringReader(RESOURCE_STRING));
        Mockito.when(fileObject.openReader(Mockito.anyBoolean())).thenReturn(stringReader);


        SimpleResourceReader unit = new SimpleResourceReader(fileObject);

        MatcherAssert.assertThat(unit.readAsLines(true), Matchers.contains("abc", "def", "hij"));
        Mockito.verify(stringReader, Mockito.times(1)).close();

    }

    @Test
    public void testReadAsLines_withoutTrim() throws IOException {

        final String RESOURCE_STRING = "abc \n  def\n  hij  ";

        FileObject fileObject = Mockito.mock(FileObject.class);
        StringReader stringReader = Mockito.spy(new StringReader(RESOURCE_STRING));
        Mockito.when(fileObject.openReader(Mockito.anyBoolean())).thenReturn(stringReader);


        SimpleResourceReader unit = new SimpleResourceReader(fileObject);

        MatcherAssert.assertThat(unit.readAsLines(false), Matchers.contains("abc ", "  def", "  hij  "));
        Mockito.verify(stringReader, Mockito.times(1)).close();

    }

    @Test
    public void testReadAsProperties() throws IOException {

        final String RESOURCE_STRING = "a=1\nb=2\nc=3";

        FileObject fileObject = Mockito.mock(FileObject.class);
        StringReader stringReader = Mockito.spy(new StringReader(RESOURCE_STRING));
        Mockito.when(fileObject.openReader(Mockito.anyBoolean())).thenReturn(stringReader);


        SimpleResourceReader unit = new SimpleResourceReader(fileObject);

        Properties result = unit.readAsProperties();

        MatcherAssert.assertThat(result.get("a"), Matchers.equalTo((Object) "1"));
        MatcherAssert.assertThat(result.get("b"), Matchers.equalTo((Object) "2"));
        MatcherAssert.assertThat(result.get("c"), Matchers.equalTo((Object) "3"));

        Mockito.verify(stringReader, Mockito.times(1)).close();

    }


}
