package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class AbstractAnnotationProcessorTest extends AbstractAnnotationProcessorTestBaseClass {

    public AbstractAnnotationProcessorTest(String message, AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{



                        {
                                "FluentElementFilter : Do filterings",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.FIELD).getResult();
                                        MatcherAssert.assertThat(result, Matchers.hasSize(7));


                                        result = createFluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.FIELD).filterByModifiers(Modifier.PUBLIC, Modifier.STATIC).getResult();
                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                    }
                                },
                                true


                        },


                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);


                                    }
                                },
                                true


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                },
                                true


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);


                                    }
                                },
                                true


                        }

                }

        );


    }


}
