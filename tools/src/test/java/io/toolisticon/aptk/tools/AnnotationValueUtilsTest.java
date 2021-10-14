package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.annotationvalueutilstestclasses.AnnotationValueTestAnnotation;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.StandardLocation;
import java.util.Arrays;
import java.util.List;


/**
 * Integration test for {@link ElementUtils}.
 * <p/>
 * Test is executed at compile time of a test class.
 */
public class AnnotationValueUtilsTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationValueUtilsTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    // -----------------------------------
    // isXXX tests
    // -----------------------------------

    @Test
    public void annotationValueUtils_test_isLong() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isLong(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isInteger() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isInteger(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isFloat() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloat(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isDouble() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDouble(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isBoolean() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBoolean(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isString() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isString(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isChar() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isChar(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isEnum() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnum(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClass(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isAnnotation() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotation(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isArray() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);

                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.is(false));

                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(true));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------------------
    // non array type isXXX tests
    // -----------------------------------

    @Test
    public void annotationValueUtils_test_getLongValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.is(1L));

                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void annotationValueUtils_test_getIntegerValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.is(1));

                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getFloatValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.is(1.0f));

                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getDoubleValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.is(1.0));

                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getBooleanValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getStringValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.is("stringValue"));

                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getCharValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.is('C'));

                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getEnumValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")).getSimpleName().toString(), Matchers.is(StandardLocation.SOURCE_OUTPUT.name()));

                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getClassValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")).getSimpleName().toString(), Matchers.is(StandardLocation.SOURCE_OUTPUT.name()));

                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getAnnotationValueOfAttribute() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")).toString(), Matchers.is("@" + Deprecated.class.getCanonicalName()));

                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());

                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void annotationValueUtils_test_getArrayValue_and_convertAndCastAttributeValueListToArray() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);

                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                // via convertAndCastAttributeValueListToArray
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Long.class)), Matchers.contains(1L, 2L, 3L));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Integer.class)), Matchers.contains(1, 2, 3));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Boolean.class)), Matchers.contains(true, false));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Float.class)), Matchers.contains(1.0f, 2.0f, 3.0f));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Double.class)), Matchers.contains(1.0, 2.0, 3.0));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), String.class)), Matchers.contains("1", "2"));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Character.class)), Matchers.contains('1', '2'));


                TypeMirror[] typeMirrors = AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), TypeMirror.class);
                MatcherAssert.assertThat(typeMirrors.length, Matchers.is(2));
                MatcherAssert.assertThat(typeMirrors[0].toString(), Matchers.is(Long.class.getCanonicalName()));
                MatcherAssert.assertThat(typeMirrors[1].toString(), Matchers.is(Integer.class.getCanonicalName()));


                VariableElement[] enums = AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), VariableElement.class);
                MatcherAssert.assertThat(enums.length, Matchers.is(2));
                MatcherAssert.assertThat(enums[0].toString(), Matchers.is(StandardLocation.SOURCE_OUTPUT.name()));
                MatcherAssert.assertThat(enums[1].toString(), Matchers.is(StandardLocation.CLASS_OUTPUT.name()));


                AnnotationMirror[] annotationMirrors = AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), AnnotationMirror.class);
                MatcherAssert.assertThat(annotationMirrors.length, Matchers.is(1));
                MatcherAssert.assertThat(annotationMirrors[0].toString(), Matchers.is("@" + Deprecated.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_convertAndCastAttributeValueListToArray_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);

                MatcherAssert.assertThat(AnnotationValueUtils.convertAndCastAttributeValueListToArray(null, String.class), Matchers.nullValue());

                boolean exceptionThrown = false;
                try {
                    MatcherAssert.assertThat(AnnotationValueUtils.convertAndCastAttributeValueListToArray(AnnotationValueUtils.getArrayValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), null), Matchers.nullValue());
                } catch (IllegalArgumentException e) {
                    exceptionThrown = true;
                }
                MatcherAssert.assertThat("Expected that IllegalArgumentException is thrown if passed type is null", exceptionThrown);


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getArrayValue_viaConvenienceMode() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);

                // via convenience methods
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getLongValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue"))), Matchers.contains(1L, 2L, 3L));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getIntegerValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue"))), Matchers.contains(1, 2, 3));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getBooleanValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue"))), Matchers.contains(true, false));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getFloatValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue"))), Matchers.contains(1.0f, 2.0f, 3.0f));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getDoubleValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue"))), Matchers.contains(1.0, 2.0, 3.0));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getStringValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue"))), Matchers.contains("1", "2"));
                MatcherAssert.assertThat(Arrays.asList(AnnotationValueUtils.getCharValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue"))), Matchers.contains('1', '2'));


                TypeMirror[] typeMirrors = AnnotationValueUtils.getTypeAttributeValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue"));
                MatcherAssert.assertThat(typeMirrors.length, Matchers.is(2));
                MatcherAssert.assertThat(typeMirrors[0].toString(), Matchers.is(Long.class.getCanonicalName()));
                MatcherAssert.assertThat(typeMirrors[1].toString(), Matchers.is(Integer.class.getCanonicalName()));

                VariableElement[] enums = AnnotationValueUtils.getEnumValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue"));
                MatcherAssert.assertThat(enums.length, Matchers.is(2));
                MatcherAssert.assertThat(enums[0].toString(), Matchers.is(StandardLocation.SOURCE_OUTPUT.name()));
                MatcherAssert.assertThat(enums[1].toString(), Matchers.is(StandardLocation.CLASS_OUTPUT.name()));


                AnnotationMirror[] annotationMirrors = AnnotationValueUtils.getAnnotationValueArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue"));
                MatcherAssert.assertThat(annotationMirrors.length, Matchers.is(1));
                MatcherAssert.assertThat(annotationMirrors[0].toString(), Matchers.is("@" + Deprecated.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_getArrayValue_viaConvenienceMode_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                // test null safety
                MatcherAssert.assertThat(AnnotationValueUtils.getLongValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValueArray((List<? extends AnnotationValue>) null), Matchers.nullValue());


                MatcherAssert.assertThat(AnnotationValueUtils.getLongValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getIntegerValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getBooleanValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getFloatValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getDoubleValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getStringValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getCharValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getEnumValueArray((AnnotationValue) null), Matchers.nullValue());
                MatcherAssert.assertThat(AnnotationValueUtils.getAnnotationValue((AnnotationValue) null), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationValueUtils_test_isXXXAray() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);

                MatcherAssert.assertThat(AnnotationValueUtils.isLongArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isIntegerArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isBooleanArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloatArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isDoubleArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isStringArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isCharArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnumArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isClassArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(true));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotationArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(true));

                MatcherAssert.assertThat(AnnotationValueUtils.isLongArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isIntegerArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBooleanArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloatArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDoubleArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isStringArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isCharArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnumArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClassArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotationArray(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.is(false));


                MatcherAssert.assertThat(AnnotationValueUtils.isLongArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isIntegerArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isBooleanArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isFloatArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isDoubleArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isStringArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isCharArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isEnumArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isClassArray(null), Matchers.is(false));
                MatcherAssert.assertThat(AnnotationValueUtils.isAnnotationArray(null), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
