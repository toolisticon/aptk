package de.holisticon.annotationprocessortoolkit.tools.characeristicsfilter;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.ValidatorKind;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * unit test for {@link de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.GenericCharacteristicsFilter}.
 */
@RunWith(Parameterized.class)
public class GenericCharacteristicFilterTest extends AbstractAnnotationProcessorUnitTest {


    public GenericCharacteristicFilterTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource("AnnotationProcessorTestClass.java");
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "findByAll : happy path",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {

                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        List<Element> filteredList = Filters.getModifierFilter().getFilter().filterByCharacteristics(ValidatorKind.ALL_OF, false, (List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED);
                                                        MatcherAssert.assertThat("Must have exactly one element'", filteredList, Matchers.hasSize(1));
                                                        MatcherAssert.assertThat("Must find one element with name 'synchronizedMethod'", filteredList.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));

                                                        // shouldn't find anything
                                                        filteredList = Filters.getModifierFilter().getFilter().filterByCharacteristics(ValidatorKind.ALL_OF, false, (List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED, Modifier.PROTECTED);
                                                        MatcherAssert.assertThat("Must have noelement'", filteredList, Matchers.<Element>empty());


                                                    }
                                                }
                                        )
                                        .build()

                        }
                }
        );


    }

    @Test
    public void test() {
        super.test();
    }
}
