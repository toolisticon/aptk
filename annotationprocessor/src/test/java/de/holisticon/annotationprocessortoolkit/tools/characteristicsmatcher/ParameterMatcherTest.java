package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.internal.Utilities;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import org.hamcrest.MatcherAssert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link ParameterExecutableMatcher}.
 */
@RunWith(Parameterized.class)
public class ParameterMatcherTest extends AbstractAnnotationProcessorTestBaseClass {

    public ParameterMatcherTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(ElementUtils.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "ParameterExecutableMatcher match",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // find field
                                        List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                        MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                        MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                        ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                        MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);
                                        MatcherAssert.assertThat("Precondition: first parameter must be of type Boolean but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getParameters().get(0).asType().toString().equals(Boolean.class.getCanonicalName()));
                                        MatcherAssert.assertThat("Precondition: second parameter must be of type String but is " + executableElement.getParameters().get(1).asType().toString(), executableElement.getParameters().get(1).asType().toString().equals(String.class.getCanonicalName()));


                                        MatcherAssert.assertThat("Should have found matching parameters", Matchers.getParameterMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class, String.class)));

                                    }
                                },
                                true


                        },
                        {
                                "ParameterExecutableMatcher no match",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                        MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                        MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                        ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                        MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);


                                        MatcherAssert.assertThat("Should not have found matching parameters", !Matchers.getParameterMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(String.class, Boolean.class)));
                                        MatcherAssert.assertThat("Should not have found matching parameters", !Matchers.getParameterMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class)));
                                        MatcherAssert.assertThat("Should not have found matching parameters", !Matchers.getParameterMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class, String.class, String.class)));

                                    }
                                },
                                true


                        },

                }

        );


    }
}
