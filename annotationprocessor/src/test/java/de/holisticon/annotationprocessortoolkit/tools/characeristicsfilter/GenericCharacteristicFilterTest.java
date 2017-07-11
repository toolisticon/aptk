package de.holisticon.annotationprocessortoolkit.tools.characeristicsfilter;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.ValidatorKind;
import de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

/**
 * unit test for {@link de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.GenericCharacteristicsFilter}.
 */
@RunWith(Parameterized.class)
public class GenericCharacteristicFilterTest extends AbstractAnnotationProcessorTestBaseClass {


    public GenericCharacteristicFilterTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(FluentExecutableElementValidator.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "findByAll : happy path",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<Element> filteredList = Filters.getModifierFilter().getFilter().filterByCharacteristics(ValidatorKind.ALL_OF, false, (List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED);
                                        MatcherAssert.assertThat("Must have exactly one element'", filteredList, Matchers.hasSize(1));
                                        MatcherAssert.assertThat("Must find one element with name 'synchronizedMethod'", filteredList.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));

                                        // shouldn't find anything
                                        filteredList = Filters.getModifierFilter().getFilter().filterByCharacteristics(ValidatorKind.ALL_OF, false, (List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED, Modifier.PROTECTED);
                                        MatcherAssert.assertThat("Must have noelement'", filteredList, Matchers.<Element>empty());


                                    }
                                },
                                true


                        },


                }

        );


    }


}
