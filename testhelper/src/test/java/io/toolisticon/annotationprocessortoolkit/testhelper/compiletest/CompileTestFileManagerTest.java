package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Unit test for {@link CompileTestFileManager}.
 */
public class CompileTestFileManagerTest {


    // ---------------------------------------------
    // -- test InMemoryOutputStream
    // ---------------------------------------------

    public static class TestCallback implements CompileTestFileManager.OutputStreamCallback {
        private String content;

        @Override
        public void setContent(byte[] content) {
            this.content = new String(content);
        }

        public String getContent() {
            return content;
        }
    }


    @Test
    public void test_InMemoryOutputStream_callBackCalledCorrectly() throws IOException {

        TestCallback outputStreamCallback = new TestCallback();
        CompileTestFileManager.InMemoryOutputStream unit = new CompileTestFileManager.InMemoryOutputStream(outputStreamCallback);

        unit.write("TEST".getBytes());
        unit.close();

        MatcherAssert.assertThat(outputStreamCallback.getContent(), Matchers.is("TEST"));

    }

    // ---------------------------------------------
    // -- test InMemoryOutputJavaFileObject
    // ---------------------------------------------

    StandardJavaFileManager standardJavaFileManager = Mockito.mock(StandardJavaFileManager.class);

    @Test
    public void test_InMemoryOutputJavaFileObject_setContent_nullValued() throws IOException, URISyntaxException {

        CompileTestFileManager compileTestFileManager = new CompileTestFileManager(standardJavaFileManager);
        CompileTestFileManager.InMemoryOutputJavaFileObject unit = compileTestFileManager.new InMemoryOutputJavaFileObject(new URI("string://abc"), JavaFileObject.Kind.OTHER);

        unit.setContent(null);

        MatcherAssert.assertThat((String) unit.getCharContent(false), Matchers.isEmptyString());

    }

    @Test
    public void test_InMemoryOutputJavaFileObject_setContent() throws IOException, URISyntaxException {

        CompileTestFileManager compileTestFileManager = new CompileTestFileManager(standardJavaFileManager);
        CompileTestFileManager.InMemoryOutputJavaFileObject unit = compileTestFileManager.new InMemoryOutputJavaFileObject(new URI("string://abc"), JavaFileObject.Kind.OTHER);

        unit.setContent("ABC".getBytes());

        MatcherAssert.assertThat((String) unit.getCharContent(false), Matchers.is("ABC"));

    }

    @Test
    public void test_InMemoryOutputJavaFileObject_openReader() throws IOException, URISyntaxException {

        CompileTestFileManager compileTestFileManager = new CompileTestFileManager(standardJavaFileManager);
        CompileTestFileManager.InMemoryOutputJavaFileObject unit = compileTestFileManager.new InMemoryOutputJavaFileObject(new URI("string://abc"), JavaFileObject.Kind.OTHER);

        unit.setContent("ABC".getBytes());

        char[] buffer = new char["ABC".length()];
        unit.openReader(true).read(buffer, 0, buffer.length);

        MatcherAssert.assertThat(new String(buffer), Matchers.is("ABC"));

    }

    // ---------------------------------------------
    // -- test FileObjectCache
    // ---------------------------------------------


    @Test
    public void test_FileObjectCache() throws URISyntaxException {

        CompileTestFileManager compileTestFileManager = new CompileTestFileManager(standardJavaFileManager);
        CompileTestFileManager.FileObjectCache unit = compileTestFileManager.new FileObjectCache();

        JavaFileObject javaFileObject = Mockito.mock(JavaFileObject.class);

        URI uri = new URI("string://abc");

        MatcherAssert.assertThat("Should not contain file", !unit.contains(uri));

        unit.addFileObject(uri, javaFileObject);

        MatcherAssert.assertThat("Should contain file", unit.contains(uri));

        MatcherAssert.assertThat((JavaFileObject) unit.getFileObject(uri), Matchers.is(javaFileObject));


    }

    // ---------------------------------------------
    // -- test CompileTestFileManager
    // ---------------------------------------------

    @Test
    public void test_CompileTestFileManager_isSameFile_sameFile() throws URISyntaxException {

        CompileTestFileManager unit = new CompileTestFileManager(standardJavaFileManager);

        JavaFileObject javaFileObject1 = Mockito.mock(JavaFileObject.class);
        URI uri1 = new URI("string://abc");
        Mockito.when(javaFileObject1.toUri()).thenReturn(uri1);

        JavaFileObject javaFileObject2 = Mockito.mock(JavaFileObject.class);
        URI uri2 = new URI("string://abc");
        Mockito.when(javaFileObject2.toUri()).thenReturn(uri2);

        MatcherAssert.assertThat("Should be detected as same file", unit.isSameFile(javaFileObject1, javaFileObject2));


    }

    @Test
    public void test_CompileTestFileManager_isSameFile_differentFile() throws URISyntaxException {

        CompileTestFileManager unit = new CompileTestFileManager(standardJavaFileManager);

        JavaFileObject javaFileObject1 = Mockito.mock(JavaFileObject.class);
        URI uri1 = new URI("string://abc");
        Mockito.when(javaFileObject1.toUri()).thenReturn(uri1);

        JavaFileObject javaFileObject2 = Mockito.mock(JavaFileObject.class);
        URI uri2 = new URI("string://def");
        Mockito.when(javaFileObject2.toUri()).thenReturn(uri2);

        MatcherAssert.assertThat("Should be detected as different file", !unit.isSameFile(javaFileObject1, javaFileObject2));


    }

    @Test(expected = FileNotFoundException.class)
    public void test_CompileTestFileManager_getFileForInput_nonExistingFile() throws IOException {

        CompileTestFileManager unit = new CompileTestFileManager(standardJavaFileManager);

        unit.getFileForInput(StandardLocation.SOURCE_OUTPUT, "de.toolisticon", "Test");


    }

    @Test
    public void test_CompileTestFileManager_getFileForInput_existingFile() throws IOException {

        CompileTestFileManager unit = new CompileTestFileManager(standardJavaFileManager);

        FileObject fileObject1 = unit.getFileForOutput(StandardLocation.SOURCE_OUTPUT, "de.toolisticon", "Test", null);
        Writer writer = fileObject1.openWriter();
        writer.write("ABC");
        writer.flush();
        writer.close();

        FileObject fileObject2 = unit.getFileForInput(StandardLocation.SOURCE_OUTPUT, "de.toolisticon", "Test");


        MatcherAssert.assertThat((String) fileObject2.getCharContent(false), Matchers.is("ABC"));


    }


    @Test(expected = FileNotFoundException.class)
    public void test_CompileTestFileManager_getJavaFileForInput_nonExistingFile() throws IOException {

        CompileTestFileManager unit = new CompileTestFileManager(standardJavaFileManager);

        unit.getJavaFileForInput(StandardLocation.SOURCE_OUTPUT, "de.toolisticon.Test", JavaFileObject.Kind.SOURCE);


    }

    @Test
    public void test_CompileTestFileManager_getJavaFileForInput_existingFile() throws IOException {

        CompileTestFileManager unit = new CompileTestFileManager(standardJavaFileManager);

        JavaFileObject fileObject1 = unit.getJavaFileForOutput(StandardLocation.SOURCE_OUTPUT, "de.toolisticon.Test", JavaFileObject.Kind.SOURCE, null);

        Writer writer = fileObject1.openWriter();
        writer.write("ABC");
        writer.flush();
        writer.close();

        FileObject fileObject2 = unit.getJavaFileForInput(StandardLocation.SOURCE_OUTPUT, "de.toolisticon.Test", JavaFileObject.Kind.SOURCE);


        MatcherAssert.assertThat((String) fileObject2.getCharContent(false), Matchers.is("ABC"));


    }



}
