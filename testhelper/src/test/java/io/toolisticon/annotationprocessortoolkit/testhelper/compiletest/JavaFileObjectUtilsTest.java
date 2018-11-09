package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Unit test for {@link JavaFileObjectUtils}.
 */
public class JavaFileObjectUtilsTest {

    // ------------------------------------------------
    // -- test JavaSourceFromString
    // ------------------------------------------------

    @Test
    public void test_JavaSourceFromString_getCharContent() throws IOException {

        final String content = "test";

        JavaFileObject fileObject = JavaFileObjectUtils.readFromString("abc", content);

        MatcherAssert.assertThat((String) fileObject.getCharContent(false), Matchers.is(content));

    }

    @Test
    public void test_JavaSourceFromString_openReader() throws IOException {

        final String content = "test";

        JavaFileObject fileObject = JavaFileObjectUtils.readFromString("abc", content);

        BufferedReader bufferedReader = new BufferedReader(fileObject.openReader(false));

        MatcherAssert.assertThat(bufferedReader.readLine(), Matchers.is(content));
    }

    @Test
    public void test_JavaSourceFromString_openInputStream() throws IOException {

        final String content = "test";

        JavaFileObject fileObject = JavaFileObjectUtils.readFromString("abc", content);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileObject.openInputStream()));

        MatcherAssert.assertThat(bufferedReader.readLine(), Matchers.is(content));
    }

    // ------------------------------------------------
    // -- test JavaSourceFromResource
    // ------------------------------------------------

    @Test
    public void test_JavaSourceFromResource_getCharContent() throws IOException {

        final String content = "package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;";

        JavaFileObject fileObject = JavaFileObjectUtils.readFromResource("/compiletests/javafileobjectutilstest/JavaSourceFromResourceTestClass.java");

        // check first line
        MatcherAssert.assertThat((String) fileObject.getCharContent(false), Matchers.containsString(content));

    }

    @Test
    public void test_JavaSourceFromResource_openReader() throws IOException {

        final String content = "package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;";

        JavaFileObject fileObject = JavaFileObjectUtils.readFromResource("/compiletests/javafileobjectutilstest/JavaSourceFromResourceTestClass.java");

        BufferedReader bufferedReader = new BufferedReader(fileObject.openReader(false));

        // check first line
        MatcherAssert.assertThat(bufferedReader.readLine(), Matchers.is(content));
    }

    @Test
    public void test_JavaSourceFromResource_openInputStream() throws IOException {

        final String content = "package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;";

        JavaFileObject fileObject = JavaFileObjectUtils.readFromResource("/compiletests/javafileobjectutilstest/JavaSourceFromResourceTestClass.java");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileObject.openInputStream()));

        // check first line
        MatcherAssert.assertThat(bufferedReader.readLine(), Matchers.is(content));
    }

    // ------------------------------------------------
    // -- test JavaSourceFromResource
    // ------------------------------------------------

    @Test
    public void test_JavaSourceFromUrl_getCharContent() throws IOException, URISyntaxException {

        final String content = "package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;";


        URL url = getClass().getResource("/compiletests/javafileobjectutilstest/JavaSourceFromResourceTestClass.java");

        JavaFileObject fileObject = JavaFileObjectUtils.readFromUrl(url);

        // check first line
        MatcherAssert.assertThat((String) fileObject.getCharContent(false), Matchers.containsString(content));

    }

    @Test
    public void test_JavaSourceFromUrl_openReader() throws IOException, URISyntaxException {

        final String content = "package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;";

        URL url = getClass().getResource("/compiletests/javafileobjectutilstest/JavaSourceFromResourceTestClass.java");


        JavaFileObject fileObject = JavaFileObjectUtils.readFromUrl(url);

        BufferedReader bufferedReader = new BufferedReader(fileObject.openReader(false));

        // check first line
        MatcherAssert.assertThat(bufferedReader.readLine(), Matchers.is(content));
    }

    @Test
    public void test_JavaSourceFromUrl_openInputStream() throws IOException, URISyntaxException {

        final String content = "package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;";

        URL url = getClass().getResource("/compiletests/javafileobjectutilstest/JavaSourceFromResourceTestClass.java");

        JavaFileObject fileObject = JavaFileObjectUtils.readFromUrl(url);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileObject.openInputStream()));

        // check first line
        MatcherAssert.assertThat(bufferedReader.readLine(), Matchers.is(content));
    }

    // ------------------------------------------------
    // -- test null safety of static accessors
    // ------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void test_nullSafety_readFromResource() {
        JavaFileObject fileObject = JavaFileObjectUtils.readFromResource(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nullSafety_readFromResource_withLocationAnd() {
        JavaFileObject fileObject = JavaFileObjectUtils.readFromResource(null, this.getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nullSafety_readFromUrl() throws URISyntaxException {
        JavaFileObject fileObject = JavaFileObjectUtils.readFromUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nullSafety_readFromString_locationIsNull() {
        JavaFileObject fileObject = JavaFileObjectUtils.readFromString(null, "TEST");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nullSafety_readFromString_contentIsNull() {
        JavaFileObject fileObject = JavaFileObjectUtils.readFromString("TEST", null);
    }

}
