package io.toolisticon.annotationprocessortoolkit.generators;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.StringReader;

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

        MatcherAssert.assertThat(unit.readLine(), org.hamcrest.Matchers.equalTo("abc"));
        MatcherAssert.assertThat(unit.readLine(), org.hamcrest.Matchers.equalTo("def"));
        MatcherAssert.assertThat(unit.readLine(), org.hamcrest.Matchers.equalTo("hij"));
        MatcherAssert.assertThat(unit.readLine(), org.hamcrest.Matchers.nullValue());

        unit.close();

        Mockito.verify(stringReader, Mockito.times(1)).close();

    }


}
