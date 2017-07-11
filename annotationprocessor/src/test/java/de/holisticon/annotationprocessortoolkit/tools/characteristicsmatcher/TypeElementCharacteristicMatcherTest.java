package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import org.hamcrest.MatcherAssert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link ParameterExecutableMatcher}.
 */
@RunWith(Parameterized.class)
public class TypeElementCharacteristicMatcherTest extends AbstractAnnotationProcessorTestBaseClass {

    public TypeElementCharacteristicMatcherTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(ElementUtils.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "TypeElementCharacteristicMatcherTest match",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "publicField");
                                        MatcherAssert.assertThat("Precondition: should have found one field", result.size() == 1);
                                        MatcherAssert.assertThat("Precondition: dound method has to be of type ExecutableElement", result.get(0) instanceof VariableElement);


                                        MatcherAssert.assertThat("Should have found matching type", Matchers.getRawTypeMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(result.get(0), String.class));

                                    }
                                },
                                true


                        },
                        {
                                "TypeElementCharacteristicMatcherTest no match",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "publicField");
                                        MatcherAssert.assertThat("Precondition: should have found one field", result.size() == 1);
                                        MatcherAssert.assertThat("Precondition: dound method has to be of type ExecutableElement", result.get(0) instanceof VariableElement);


                                        MatcherAssert.assertThat("Should not have found matching type", !Matchers.getRawTypeMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(result.get(0), Boolean.class));

                                    }
                                },
                                true


                        },

                }

        );


    }
}
