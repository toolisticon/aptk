package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;

/**
 * Test class for {@link AnnotationMirrorWrapper}.
 */
public class AnnotationMirrorWrapperTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface MyTestAnnotation {
        String value();

        int intValue() default 1;
    }

    @PassIn
    @MyTestAnnotation(value = "XOXO")
    static class MyTestClass {

    }

    @Test
    public void test_wrap_and_unwrap() {
        AnnotationMirror annotationMirror = Mockito.mock(AnnotationMirror.class);

        MatcherAssert.assertThat(AnnotationMirrorWrapper.wrap(annotationMirror).unwrap(), Matchers.is(annotationMirror));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_wrap_null() {
        AnnotationMirrorWrapper.wrap(null);
    }

    @Test
    public void test_getAttribute() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
            MatcherAssert.assertThat(unit.getAttribute().get().getStringValue(), Matchers.is("XOXO"));
            MatcherAssert.assertThat(unit.getAttribute("value").get().getStringValue(), Matchers.is("XOXO"));
            MatcherAssert.assertThat("Must not have attribute value for default value", !unit.getAttribute("intValue").isPresent());
            //MatcherAssert.assertThat("Must not have attribute value for null valued name", !unit.getAttribute(null).isPresent());

        }).executeTest();

    }

    @Test
    public void test_getAttribute_passNullName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                        unit.getAttribute(null);


                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).expectedThrownException(IllegalArgumentException.class)
                .executeTest();

    }

    @Test
    public void test_getAttribute_invalidAttributeName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                        unit.getAttribute("XYZ");


                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).expectedThrownException(IllegalArgumentException.class)
                .executeTest();

    }

    @Test
    public void test_getAttributeWithDefault() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                MatcherAssert.assertThat(unit.getAttributeWithDefault("value").getStringValue(), Matchers.is("XOXO"));
                MatcherAssert.assertThat(unit.getAttributeWithDefault().getStringValue(), Matchers.is("XOXO"));
                MatcherAssert.assertThat(unit.getAttributeWithDefault("intValue").getIntegerValue(), Matchers.is(1));
                //MatcherAssert.assertThat("Must not have attribute value for null valued name", !unit.getAttributeWithDefault(null).isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        }).executeTest();

    }

    @Test
    public void test_getAttributeWithDefault_passNullName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                unit.getAttributeWithDefault(null);


            } finally {
                ToolingProvider.clearTooling();
            }

        }).expectedThrownException(IllegalArgumentException.class)
                .executeTest();

    }

    @Test
    public void test_getAttributeWithDefault_invalidAttributeName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                unit.getAttributeWithDefault("XYZ");


            } finally {
                ToolingProvider.clearTooling();
            }

        }).expectedThrownException(IllegalArgumentException.class)
                .executeTest();

    }

    @Test
    public void test_getAttributeNames() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                MatcherAssert.assertThat(unit.getAttributeNames(), Matchers.containsInAnyOrder("value", "intValue"));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).executeTest();

    }

    @Test
    public void test_hasAttribute() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper unit = ElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();
                MatcherAssert.assertThat("Should find value attribute", unit.hasAttribute("value"));
                MatcherAssert.assertThat("Should find value attribute", unit.hasAttribute("intValue"));
                MatcherAssert.assertThat("Should not find nonexisting attribute", !unit.hasAttribute("abc"));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).executeTest();

    }


    @Test
    public void test_get() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                // By class
                Optional<AnnotationMirrorWrapper> result = AnnotationMirrorWrapper.get(element, MyTestAnnotation.class);
                MatcherAssert.assertThat(result.get().unwrap().getAnnotationType().toString(), Matchers.is(MyTestAnnotation.class.getCanonicalName()));
                MatcherAssert.assertThat("must return empty optional if any of the parameters is null", !AnnotationMirrorWrapper.get(null, MyTestAnnotation.class).isPresent());
                MatcherAssert.assertThat("must return empty optional if any of the parameters is null", !AnnotationMirrorWrapper.get(element, (Class<? extends Annotation>) null).isPresent());
                MatcherAssert.assertThat("must return empty optional if any of the parameters is null", !AnnotationMirrorWrapper.get(null, (Class<? extends Annotation>) null).isPresent());


                // by FQN
                result = AnnotationMirrorWrapper.get(element, MyTestAnnotation.class.getCanonicalName());
                MatcherAssert.assertThat(result.get().unwrap().getAnnotationType().toString(), Matchers.is(MyTestAnnotation.class.getCanonicalName()));
                MatcherAssert.assertThat("must return empty optional if any of the parameters is null", !AnnotationMirrorWrapper.get(null, MyTestAnnotation.class.getCanonicalName()).isPresent());
                MatcherAssert.assertThat("must return empty optional if any of the parameters is null", !AnnotationMirrorWrapper.get(element, (String) null).isPresent());
                MatcherAssert.assertThat("must return empty optional if any of the parameters is null", !AnnotationMirrorWrapper.get(null, (String) null).isPresent());


            } finally {
                ToolingProvider.clearTooling();
            }

        }).executeTest();

    }

    @Test
    public void test_asElement() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(MyTestClass.class, (processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                // By class
                Optional<AnnotationMirrorWrapper> result = AnnotationMirrorWrapper.get(element, MyTestAnnotation.class);
                MatcherAssert.assertThat(result.get().asElement().getQualifiedName(), Matchers.is(MyTestAnnotation.class.getCanonicalName()));


            } finally {
                ToolingProvider.clearTooling();
            }

        }).executeTest();

    }


}
