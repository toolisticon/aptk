package io.toolisticon.aptk.integrationtest.java16;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeElement;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.RecordComponentElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;

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

    @Test
    public void test_recordComponent_accessWrapperMethods() {
        Cute.unitTest().when().passInElement().<TypeElement>fromClass(MyRecord.class).intoUnitTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);
                TypeElementWrapper typeElement = TypeElementWrapper.wrap(element);

                RecordComponentElementWrapper elementWrapper = typeElement.getRecordComponents().get(0);
                MatcherAssert.assertThat(elementWrapper.getSimpleName(), Matchers.is("name"));
                MatcherAssert.assertThat("Must be methods", elementWrapper.getAccessor().isMethod());
                MatcherAssert.assertThat(elementWrapper.getEnclosingRecordTypeElement().getQualifiedName(), Matchers.is(MyRecord.class.getCanonicalName()));
                MatcherAssert.assertThat(TypeElementWrapper.toTypeElement(elementWrapper.getEnclosingElement().get()).getQualifiedName(), Matchers.is(MyRecord.class.getCanonicalName()));

                // Additional method
                MatcherAssert.assertThat(elementWrapper.getField(), Matchers.notNullValue());
                MatcherAssert.assertThat(elementWrapper.getField().getSimpleName(), Matchers.is("name"));


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

                MatcherAssert.assertThat(enclosedTypeElements, Matchers.containsInAnyOrder(
                		MyRecord.class.getCanonicalName(),GetOuterTypeTestRecord.class.getCanonicalName(), 
                		GetOuterTypeTestRecord.InnerMostClass.class.getCanonicalName(),
                		GetInnerTypeRecordTest.class.getCanonicalName(),
                		GetInnerTypeRecordTest.InnerRecordType.class.getCanonicalName()
                		
                		));

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

                MatcherAssert.assertThat(enclosedTypeElements, Matchers.hasSize(3));
                MatcherAssert.assertThat(enclosedTypeElements, Matchers.containsInAnyOrder(
                		MyRecord.class.getCanonicalName(),
                		GetOuterTypeTestRecord.class.getCanonicalName(), 
                		GetInnerTypeRecordTest.InnerRecordType.class.getCanonicalName()));

            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();

    }
    
    
    
    record GetOuterTypeTestRecord() {

        
        			
			@PassIn
			static class InnerMostClass {
				
			}
        			
     
    }

    @Test
    public void test_TypeElementWrapper_getOuterType() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetOuterTypeTestRecord.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        Optional<TypeElementWrapper> outerType = unit.getOuterType();
                        MatcherAssert.assertThat(outerType.get().getQualifiedName(), Matchers.is(GetOuterTypeTestRecord.class.getCanonicalName()));
                        
     
                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }
    
    
    @PassIn
    static class GetInnerTypeRecordTest {

        
        			
			
			record InnerRecordType() {
				
			}
        			
     
    }
    
    
    @Test
    public void test_TypeElementWrapper_getInnerType() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetInnerTypeRecordTest.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        List<TypeElementWrapper> innerTypes = unit.getInnerTypes();
                        MatcherAssert.assertThat(innerTypes.stream().map(TypeElementWrapper::getQualifiedName).collect(Collectors.toList()), Matchers.contains(GetInnerTypeRecordTest.InnerRecordType.class.getCanonicalName()));
                        
     
                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }
    
    

}
