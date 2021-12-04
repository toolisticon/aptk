package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AbstractAnnotationProcessorTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    private static class AnnotationProcessorUnitTestClass {

        private String privateField;
        protected String protectedField;
        String packagePrivateField;
        public String publicField;
        public final String publicFinalField = "";
        public static String publicStaticField;
        public transient String publicTransientField;

        enum TestEnum1 {
            TEST11, TEST12;
        }

        public enum TestEnum2 {
            TEST21, TEST22;
        }

        public static class EmbeddedStaticClass {

        }

        public Comparator<Long> comparatorWithAnonymousClass = new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return 0;
            }
        };


        public class EmbeddedClass {

        }

        public class EmbeddedClassWithNoNoargConstructor {

            public EmbeddedClassWithNoNoargConstructor(String abs) {

            }

        }

        public abstract class AbstractEmbeddedClass {

            public abstract void abstractMethod();

        }

        {
            int x = 0;
        }

        static {
            int y = 0;
        }

        public AnnotationProcessorUnitTestClass() {

        }

        public AnnotationProcessorUnitTestClass(String withParameter) {

        }

        public String methodWithReturnTypeAndParameters(Boolean first, String second) {
            return "";
        }


        public int testGenericsOnParameter(Map<String, Comparator<Long>> o1, Map<? extends StringBuilder, Comparator<? super List<?>>> o2) {
            return 0;
        }

    }

    @Test
    public void fluentElementFilter_doFilterings() {

        CompileTestBuilder.unitTest()
                .defineTest(new UnitTest<TypeElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement1) {

                        // init processor tools and get element used for tests
                        ToolingProvider.setTooling(processingEnvironment);
                        Element element = TypeUtils.TypeRetrieval.getTypeElement(AnnotationProcessorUnitTestClass.class);


                        List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                .getResult();
                        MatcherAssert.assertThat(result, Matchers.hasSize(8));


                        result = FluentElementFilter.createFluentElementFilter(
                                element.getEnclosedElements())
                                .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
                                .getResult();
                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }


    @Test
    public void createSupportedAnnotationSet_withoutParameters() {

        MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(), Matchers.<String>empty());

    }

    @Test
    public void createSupportedAnnotationSet_withParameters() {

        MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(Override.class, Ignore.class), Matchers.containsInAnyOrder(Override.class.getCanonicalName(), Ignore.class.getCanonicalName()));

    }

    @Test
    public void createSupportedAnnotationSet_withNullParameter() {

        MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(null), Matchers.<String>empty());
        MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(Override.class, null, Ignore.class), Matchers.containsInAnyOrder(Override.class.getCanonicalName(), Ignore.class.getCanonicalName()));

    }

    @Test
    public void wrapToArrayt_withNullParameter() {

        MatcherAssert.assertThat("Should return null", AbstractAnnotationProcessor.wrapToArray((String) null)[0] == null);

        Class[] resultArray = AbstractAnnotationProcessor.wrapToArray(Override.class, null, Ignore.class);
        MatcherAssert.assertThat(Arrays.asList(resultArray), Matchers.hasSize(3));
        MatcherAssert.assertThat(resultArray[0], Matchers.equalTo((Class) Override.class));
        MatcherAssert.assertThat(resultArray[1], Matchers.nullValue());
        MatcherAssert.assertThat(resultArray[2], Matchers.equalTo((Class) Ignore.class));

    }


}
