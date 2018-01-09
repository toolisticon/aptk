package io.toolisticon.annotationprocessortoolkit.tools;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.annotationvalueutilstestclasses.AnnotationValueTestAnnotation;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.util.Arrays;
import java.util.List;


/**
 * Integration test for {@link ElementUtils}.
 * <p/>
 * Test is executed at compile time of a test class.
 */
@RunWith(Parameterized.class)
public class AnnotationValueUtilsTest extends AbstractAnnotationProcessorUnitTest {

    public AnnotationValueUtilsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(


                new Object[][]{


                        // -----------------------------------
                        // isXXX tests
                        // -----------------------------------

                        {
                                "AnnotationValueUtils test - isLong",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isInteger",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isFloat",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isDouble",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isBoolean",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isString",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isChar",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isEnum",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isClass",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isAnnotation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - isArray",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        // -----------------------------------
                        // non array type isXXX tests
                        // -----------------------------------
                        {
                                "AnnotationValueUtils test - getLongValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getIntegerValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getFloatValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getDoubleValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getBooleanValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getStringValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getCharValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getEnumValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getClassValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, AnnotationValueTestAnnotation.class);
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classValue")).toString(), Matchers.is(Long.class.getCanonicalName()));

                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationValue")), Matchers.nullValue());

                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "longArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "intArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "booleanArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "floatArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "doubleArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "stringArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "charArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "enumArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "classArrayValue")), Matchers.nullValue());
                                                              MatcherAssert.assertThat(AnnotationValueUtils.getClassValue(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, "annotationArrayValue")), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getAnnotationValueOfAttribute",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getArrayValue and convertAndCastAttributeValueListToArray",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "AnnotationValueUtils test - getArrayValue via convenience methods",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },

                }

        );


    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource("AnnotationValueUtilsTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }
}