package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.util.stream.Collectors;

public class Java17Tests {


    @Test
    public void test_sealedClassesFeature_unsealed() {
        Cute.unitTest().when().passInElement().<TypeElement>fromClass(Java17Tests.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                TypeElementWrapper elementWrapper = TypeElementWrapper.wrap(element);

                MatcherAssert.assertThat(elementWrapper.getPermittedSubclasses(), Matchers.empty());
                MatcherAssert.assertThat(element.getPermittedSubclasses(), Matchers.empty());

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }



    static final class AllowedClass extends SealedClass {

    }

    @PassIn
    static sealed class SealedClass permits AllowedClass{

    }



    @Test
    public void test_sealedClassesFeature_sealed() {
        Cute.unitTest().when().passInElement().<TypeElement>fromClass(SealedClass.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                TypeElementWrapper elementWrapper = TypeElementWrapper.wrap(element);

                MatcherAssert.assertThat(elementWrapper.getPermittedSubclasses().stream().map(e -> e.getQualifiedName()).collect(Collectors.toSet()), Matchers.contains(SealedClass.class.getCanonicalName()));
                MatcherAssert.assertThat(element.getPermittedSubclasses().stream().map(e -> e.toString()).collect(Collectors.toSet()), , Matchers.contains(SealedClass.class.getCanonicalName()));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }


}
