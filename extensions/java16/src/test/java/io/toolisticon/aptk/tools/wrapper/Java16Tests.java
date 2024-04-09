package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.util.Set;
import java.util.stream.Collectors;

public class Java16Tests {

    @PassIn
    record MyRecord(String name, String surname) {
    }

    @Test
    public void test_records_isRecord() {
        Cute.unitTest().when().passInElement().fromClass(MyRecord.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                ElementWrapper<?> elementWrapper = ElementWrapper.wrap(element);

                MatcherAssert.assertThat("Should detect record ", elementWrapper.isRecord());
                MatcherAssert.assertThat("Should not detect record ", !TypeElementWrapper.getByClass(Java16Tests.class).get().isRecord());

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }

    @Test
    public void test_records_isTypeElement() {
        Cute.unitTest().when().passInElement().fromClass(MyRecord.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                ElementWrapper<?> elementWrapper = ElementWrapper.wrap(element);

                MatcherAssert.assertThat("Should detect record ", elementWrapper.isTypeElement());
                MatcherAssert.assertThat("Should return false", !elementWrapper.isExecutableElement());
                MatcherAssert.assertThat("Should return false ", !elementWrapper.isVariableElement());
                MatcherAssert.assertThat("Should return false ", !elementWrapper.isTypeParameterElement());
                MatcherAssert.assertThat("Should return false", !elementWrapper.isModuleElement());

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }


    @Test
    public void test_recordComponent_isRecordComponent() {
        Cute.unitTest().when().passInElement().fromClass(MyRecord.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                TypeElement typeElement = (TypeElement) element;

                ElementWrapper<?> elementWrapper = ElementWrapper.wrap(typeElement.getRecordComponents().get(0));

                MatcherAssert.assertThat("Should detect record ", elementWrapper.isRecordComponent());
                MatcherAssert.assertThat("Should not detect record ", !TypeElementWrapper.getByClass(Java16Tests.class).get().isRecordComponent());

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }

    @Test
    public void test_recordComponent_isTypeElement() {
        Cute.unitTest().when().passInElement().fromClass(MyRecord.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                TypeElement typeElement = (TypeElement) element;

                ElementWrapper<?> elementWrapper = ElementWrapper.wrap(typeElement.getRecordComponents().get(0));

                MatcherAssert.assertThat("Should detect RecordComponentElement ", elementWrapper.isRecordComponentElement());
                MatcherAssert.assertThat("Should detect false ", !elementWrapper.isTypeElement());
                MatcherAssert.assertThat("Should return false", !elementWrapper.isExecutableElement());
                MatcherAssert.assertThat("Should return false ", !elementWrapper.isVariableElement());
                MatcherAssert.assertThat("Should return false ", !elementWrapper.isTypeParameterElement());
                MatcherAssert.assertThat("Should return false", !elementWrapper.isModuleElement());

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }


    public void test_recordComponent_simpleName() {
        Cute.unitTest().when().passInElement().<TypeElement>fromClass(MyRecord.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);
                TypeElementWrapper typeElement = TypeElementWrapper.wrap(element);

                RecordComponentElementWrapper elementWrapper = typeElement.getRecordComponents().get(0);
                MatcherAssert.assertThat(elementWrapper.getSimpleName(), Matchers.is("name"));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }

    @Test
    public void test_recordComponent_filtering_byTypeElement() {
        Cute.unitTest().when((processingEnvironment) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);
                TypeElementWrapper typeElement = TypeElementWrapper.getByClass(Java16Tests.class).get();

                Set<String> enclosedTypeElements = typeElement.filterFlattenedEnclosedElementTree().applyFilter(AptkCoreMatchers.IS_TYPE_ELEMENT).getResult().stream().map(e -> e.getQualifiedName().toString()).collect(Collectors.toSet());

                MatcherAssert.assertThat(enclosedTypeElements, Matchers.contains(MyRecord.class.getCanonicalName()));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }

    @Test
    public void test_recordComponent_filtering_byRecord() {
        Cute.unitTest().when((processingEnvironment) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);
                TypeElementWrapper typeElement = TypeElementWrapper.getByClass(Java16Tests.class).get();

                Set<String> enclosedTypeElements = typeElement.filterFlattenedEnclosedElementTree().applyFilter(AptkCoreMatchers.IS_RECORD).getResult().stream().map(e -> e.getQualifiedName().toString()).collect(Collectors.toSet());

                MatcherAssert.assertThat(enclosedTypeElements, Matchers.hasSize(1));
                MatcherAssert.assertThat(enclosedTypeElements, Matchers.contains(MyRecord.class.getCanonicalName()));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }

}
