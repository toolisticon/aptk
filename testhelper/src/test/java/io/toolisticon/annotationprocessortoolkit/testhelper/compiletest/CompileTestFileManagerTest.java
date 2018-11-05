package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

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

}
