package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.impl;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.AnnotationUtils;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link FluentValidatorMessage}.
 */
@RunWith(Parameterized.class)
public class FluentValidatorMessageTest extends AbstractAnnotationProcessorUnitTest {

    public FluentValidatorMessageTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Before
    public void init() {
        CoreMatcherValidationMessages.setPrintMessageCodes(true);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "Check error element message",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentValidatorMessage message = new FluentValidatorMessage(element, Diagnostic.Kind.ERROR, "TEST ${0}", "SUCCESS");
                                                              message.issueMessage();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("TEST SUCCESS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "Check warning element message",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentValidatorMessage message = new FluentValidatorMessage(element, Diagnostic.Kind.WARNING, "TEST ${0}", "SUCCESS");
                                                              message.issueMessage();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setWarningChecks("TEST SUCCESS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "Check info element message",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentValidatorMessage message = new FluentValidatorMessage(element, Diagnostic.Kind.NOTE, "TEST ${0}", "SUCCESS");
                                                              message.issueMessage();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setNoteChecks("TEST SUCCESS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "Check element with AnnotationMirror",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> annotatedElement = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element, FilterTestAnnotation1.class);
                                                              MatcherAssert.assertThat("PRECONDITION : Must have found one element", annotatedElement.size() == 1);
                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(annotatedElement.get(0), FilterTestAnnotation1.class);
                                                              MatcherAssert.assertThat("PRECONDITION : Must have found one annotation", annotationMirror, Matchers.notNullValue());


                                                              FluentValidatorMessage message = new FluentValidatorMessage(annotatedElement.get(0),annotationMirror, Diagnostic.Kind.NOTE, "TEST ${0}", "SUCCESS");
                                                              message.issueMessage();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Check element with AnnotationMirror and AnnotationValue",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> annotatedElement = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element, FilterTestAnnotation1.class);
                                                              MatcherAssert.assertThat("PRECONDITION : Must have found one element", annotatedElement.size() == 1);
                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(annotatedElement.get(0), FilterTestAnnotation1.class);
                                                              MatcherAssert.assertThat("PRECONDITION : Must have found one annotation", annotationMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : Must have found one annotation", annotationMirror.getElementValues().size() == 1);

                                                              FluentValidatorMessage message = new FluentValidatorMessage(element, annotatedElement.get(0).getAnnotationMirrors().get(0), annotationMirror.getElementValues().get(0), Diagnostic.Kind.NOTE, "TEST ${0}", "SUCCESS");
                                                              message.issueMessage();

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
        return JavaFileObjects.forResource("AnnotationProcessorTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }

    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
