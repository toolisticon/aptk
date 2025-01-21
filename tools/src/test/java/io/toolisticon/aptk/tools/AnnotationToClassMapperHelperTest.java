package io.toolisticon.aptk.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.toolisticon.aptk.api.AnnotationToClassMapper;
import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.PassIn;

/**
 * Unit Test for 
 * {@link AnnotationToClassMapper}
 */

public class AnnotationToClassMapperHelperTest {
	
    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useCompilerOptions("-parameters");

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }
    
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AnnotationToClassMapper(mappedClass = CorrectMappingWithParameters.CorrectMappingWithParametersImpl.class, mappedAttributeNames = {"aString","", "anArray", "aClass", "{wtf}", "aLong"})
    @interface CorrectMappingWithParameters {
    	String aString();
    	long aLong();
    	Class<?> aClass();
    	double[] anArray();
    
    	static class CorrectMappingWithParametersImpl {
    		
    		public CorrectMappingWithParametersImpl(String a, String param, double[] anArray, Class<?> b, String wtf, long c) {
    			
    		}
    		
    	}
    
    }
    
    interface TestClassWithCorrectMapping {
    	
    	
    	void oneCrazyMapping(@PassIn @CorrectMappingWithParameters(aString = "YES" , aLong = 4L, aClass = String.class, anArray = {2.0, 3.0, 4.0}) String param);
    	
    }
    
    
    @Test
    public void test_correctMapping() {
    	
    	unitTestBuilder.<VariableElement>defineTestWithPassedInElement(TestClassWithCorrectMapping.class, (processingEnvironment, element) -> {
    		
    		try {
    			ToolingProvider.setTooling(processingEnvironment);
    		
	    		VariableElementWrapper parameterElement = VariableElementWrapper.wrap(element);
	    		AnnotationMirrorWrapper mappingAnnotation = parameterElement.getAnnotationMirror(CorrectMappingWithParameters.class).get();
	    		AnnotationToClassMapperHelper helper = AnnotationToClassMapperHelper.getInstance(parameterElement,mappingAnnotation);
	    		
	    		
	    		MatcherAssert.assertThat("Must be valid", helper.validate());
	    		MatcherAssert.assertThat(helper.createInstanceInitializationCommand(), Matchers.in(
	    				Arrays.asList("new io.toolisticon.aptk.tools.AnnotationToClassMapperHelperTest.CorrectMappingWithParameters.CorrectMappingWithParametersImpl(\"YES\", param, new double[]{2.0, 3.0, 4.0}, java.lang.String.class, wtf, 4L)",
	    						"new io.toolisticon.aptk.tools.AnnotationToClassMapperHelperTest.CorrectMappingWithParameters.CorrectMappingWithParametersImpl(\"YES\", arg0, new double[]{2.0, 3.0, 4.0}, java.lang.String.class, wtf, 4L)")));
	    		
	    		
    		} finally {
    			ToolingProvider.clearTooling();
    		}
    		
    		
    	})
    	.compilationShouldSucceed()
    	.executeTest();
    	
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AnnotationToClassMapper(mappedClass = IncorrectMappingWithNoMatchingConstructor.IncorrectMappingWithNoMatchingConstructorImpl.class, mappedAttributeNames = {"aString","", "anArray", "aClass", "aLong"})
    @interface IncorrectMappingWithNoMatchingConstructor {
    	String aString();
    	long aLong();
    	Class<?> aClass();
    	double[] anArray();
    
    	static class IncorrectMappingWithNoMatchingConstructorImpl {
    		
    		public IncorrectMappingWithNoMatchingConstructorImpl(String a, String param, double[] anArray, long c, Class<?> b) {
    			
    		}
    		
    	}
    
    }
    
    interface TestClassWithIncorrectMappingWithNoMatchingConstructor {
    	
    	
    	void oneCrazyMapping(@PassIn @IncorrectMappingWithNoMatchingConstructor(aString = "YES" , aLong = 4L, aClass = String.class, anArray = {2.0, 3.0, 4.0}) String param);
    	
    }
	
    @Test
    public void test_incorrectMappingWithNoMatchingConstructor() {
    	
    	unitTestBuilder.<VariableElement>defineTestWithPassedInElement(TestClassWithIncorrectMappingWithNoMatchingConstructor.class, (processingEnvironment, element) -> {
    		
    		try {
    			ToolingProvider.setTooling(processingEnvironment);
    		
	    		VariableElementWrapper parameterElement = VariableElementWrapper.wrap(element);
	    		AnnotationMirrorWrapper mappingAnnotation = parameterElement.getAnnotationMirror(IncorrectMappingWithNoMatchingConstructor.class).get();
	    		AnnotationToClassMapperHelper helper = AnnotationToClassMapperHelper.getInstance(parameterElement,mappingAnnotation);
	    		
	    		
	    		MatcherAssert.assertThat("Must not be invalid", !helper.validate());
	    		
	    		
    		} finally {
    			ToolingProvider.clearTooling();
    		}
    		
    	})
    	.compilationShouldFail()
    	.expectErrorMessageThatContains("NO_MATCHING_CONSTRUCTOR")
    	.executeTest();
    	
    }

    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AnnotationToClassMapper(mappedClass = IncorrectMappingWithNoNoargConstructor.IncorrectMappingWithNoNoargConstructorImpl.class, mappedAttributeNames = {})
    @interface IncorrectMappingWithNoNoargConstructor {
    	
    
    	static class IncorrectMappingWithNoNoargConstructorImpl {
    		
    		public IncorrectMappingWithNoNoargConstructorImpl(String a, String param, double[] anArray, long c, Class<?> b) {
    			
    		}
    		
    	}
    
    }
    
    interface TestClassWithIncorrectMappingWithNoNoargConstructor {
    	
    	
    	void oneCrazyMapping(@PassIn @IncorrectMappingWithNoNoargConstructor() String param);
    	
    }
	
    @Test
    public void test_incorrectMappingWithNoNoargConstructor() {
    	
    	unitTestBuilder.<VariableElement>defineTestWithPassedInElement(TestClassWithIncorrectMappingWithNoNoargConstructor.class, (processingEnvironment, element) -> {
    		
    		try {
    			ToolingProvider.setTooling(processingEnvironment);
    		
	    		VariableElementWrapper parameterElement = VariableElementWrapper.wrap(element);
	    		AnnotationMirrorWrapper mappingAnnotation = parameterElement.getAnnotationMirror(IncorrectMappingWithNoNoargConstructor.class).get();
	    		AnnotationToClassMapperHelper helper = AnnotationToClassMapperHelper.getInstance(parameterElement,mappingAnnotation);
	    		
	    		
	    		MatcherAssert.assertThat("Must not be invalid", !helper.validate());
	    		
	    		
    		} finally {
    			ToolingProvider.clearTooling();
    		}
    		
    	})
    	.compilationShouldFail()
    	.expectErrorMessageThatContains("MISSING_NOARG_CONSTRUCTOR")
    	.executeTest();
    	
    }
    
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @AnnotationToClassMapper(mappedClass = IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement.IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElementImpl.class, mappedAttributeNames = {"a", ""})
    @interface IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement {
    	
    	String a();
    	
 
    
    	static class IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElementImpl {
    		
    		public IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElementImpl(String a, String b) {
    			
    		}
    		
    	}
    
    }
    
    @PassIn @IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement(a="")
    interface TestClassWithIncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement {
    	
    	
    	void oneCrazyMapping(String param);
    	
    }
	
    @Test
    public void test_incorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement() {
    	
    	unitTestBuilder.<TypeElement>defineTestWithPassedInElement(TestClassWithIncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement.class, (processingEnvironment, element) -> {
    		
    		try {
    			ToolingProvider.setTooling(processingEnvironment);
    		
    			TypeElementWrapper parameterElement = TypeElementWrapper.wrap(element);
	    		AnnotationMirrorWrapper mappingAnnotation = parameterElement.getAnnotationMirror(IncorrectMappingWithMethodParameterMappingOnAnnotatedTypeElement.class).get();
	    		AnnotationToClassMapperHelper helper = AnnotationToClassMapperHelper.getInstance(parameterElement,mappingAnnotation);
	    		
	    		
	    		MatcherAssert.assertThat("Must not be invalid", !helper.validate());
	    		
	    		
    		} finally {
    			ToolingProvider.clearTooling();
    		}
    		
    	})
    	.compilationShouldFail()
    	.expectErrorMessageThatContains("INCORRECT_METHOD_PARAMETER_MAPPING")
    	.executeTest();
    	
    }
}
