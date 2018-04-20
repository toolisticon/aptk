package io.toolisticon.annotationprocessortoolkit.tools.filter;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
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
 * unit test for {@link InclusiveCriteriaElementFilter}.
 */
@RunWith(Parameterized.class)
public class InclusiveCriteriaElementFilterTest extends AbstractAnnotationProcessorUnitTest {


    public InclusiveCriteriaElementFilterTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
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

                                                        List<Element> filteredList = CoreMatchers.BY_MODIFIER.getFilter().filterByAllOf((List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED);
                                                        MatcherAssert.assertThat("Must have exactly one element'", filteredList, Matchers.hasSize(1));
                                                        MatcherAssert.assertThat("Must find one element with name 'synchronizedMethod'", filteredList.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));

                                                        // shouldn't find anything
                                                        filteredList = CoreMatchers.BY_MODIFIER.getFilter().filterByAllOf((List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED, Modifier.PROTECTED);
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
