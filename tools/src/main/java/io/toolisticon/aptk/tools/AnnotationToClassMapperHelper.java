package io.toolisticon.aptk.tools;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import io.toolisticon.aptk.api.AnnotationToClassMapper;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;

public class AnnotationToClassMapperHelper {

	
	static class AnnotationToClassMapperWrapper {
		
		AnnotationMirrorWrapper annotation;
		
		private AnnotationToClassMapperWrapper(AnnotationMirrorWrapper annotation) {
			this.annotation = annotation;
		}
		
		TypeMirrorWrapper mappedClass() {
			return annotation.getAttribute("mappedClass").get().getClassValue();
		}
		
		
		/**
		 * the attribute names to map against method or constructor parameters.
		 * @return
		 */
		String[] mappedAttributeNames() {
			return annotation.getAttributeWithDefault("mappedAttributeNames").getArrayValue().stream().map(e -> e.getStringValue()).collect(Collectors.toList()).toArray(new String[0]);
		}
		
		static Optional<AnnotationToClassMapperWrapper> wrap(AnnotationMirrorWrapper annotation) {
			
			if (annotation != null && annotation.asElement().hasAnnotation(AnnotationToClassMapper.class)) {
				return Optional.of(new AnnotationToClassMapperWrapper(annotation.asElement().getAnnotationMirror(AnnotationToClassMapper.class).get()));
			} else {
				return Optional.empty();
			}
			
		}
		
		static boolean hasAnnotation(AnnotationMirrorWrapper annotation) {
			return wrap(annotation).isPresent();
		}
		
		
	}
	

    private final ElementWrapper<?> elementWrapper;
    private final AnnotationMirrorWrapper annotation;

    private AnnotationToClassMapperHelper(ElementWrapper<?> elementWrapper, AnnotationMirrorWrapper annotation) {
        this.elementWrapper = elementWrapper;
        this.annotation = annotation;
    }
    
    /**
     * Gets an instance of the helper class.
     * @param elementWrapper the element wrapper of the annotated element
     * @param annotation the annotation mirror of the annotation annotated with AnnotationToClassMapper annotation
     * @return
     */
    public static AnnotationToClassMapperHelper getInstance(ElementWrapper<?> elementWrapper, AnnotationMirrorWrapper annotation) {
    	return new AnnotationToClassMapperHelper(elementWrapper, annotation);
    }

    AnnotationToClassMapperWrapper getValidatorAnnotation() {
        return AnnotationToClassMapperWrapper.wrap(this.annotation).get();
    }

    // visible for testing
    AnnotationMirrorWrapper getAnnotationMirrorWrapper() {
        return annotation;
    }


    enum InternalValidationMessages implements ValidationMessage {
    	
    	ERROR_BROKEN_VALIDATOR_ATTRIBUTE_NAME_MISMATCH ("INVALID_ATTRIBUTE_NAME", "Passed attribute names for annotation '{}' aren't valid: {}"),
    	ERROR_BROKEN_VALIDATOR_CONSTRUCTOR_PARAMETER_MAPPING ("NO_MATCHING_CONSTRUCTOR", "No matching constructor could be found for class : {}"),
    	ERROR_BROKEN_VALIDATOR_MISSING_NOARG_CONSTRUCTOR("MISSING_NOARG_CONSTRUCTOR", "Haven't found a noarg constructor for class: {}"),
    	ERROR_BROKEN_VALIDATOR_INCORRECT_METHOD_PARAMETER_MAPPING("INCORRECT_METHOD_PARAMETER_MAPPING", "Empty attributeNames can only be used if annotated element represents a method parameter"),
    	;

    	private final String code;
    	
    	private final String message;
    	
    	InternalValidationMessages(String code, String message) {
    		this.code = code;
    		this.message = message;
    	}
    	
    	
		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getMessage() {
			return message;
		}
    	
    	
    	
    }
    
    private boolean isLocaleVariableName(String name) {
    	return name.matches("[{].*[}]");
    }
    
    private String getLocalVariableName(String name) {
    	Pattern pattern = Pattern.compile("[{](.*)[}]");
    	Matcher matcher = pattern.matcher(name);
    	return matcher.matches()? matcher.group(1): null;
    }
    
    
    /**
     * Validates if the annotation has been properly configured and if constructor is available.
     * @return true if annotion configuration is correct and constructor is available, otherwise false
     */
    public boolean validate() {
        // must check if parameter types are assignable
    	AnnotationToClassMapperWrapper mapperAnnotation = getValidatorAnnotation();
        TypeMirrorWrapper mappedTypeMirror = mapperAnnotation.mappedClass();
        String[] attributeNamesToConstructorParameterMapping = mapperAnnotation.mappedAttributeNames();

       
        
        
        if (attributeNamesToConstructorParameterMapping.length > 0) {

            // First check if annotation attribute Names are correct
            String[] invalidNames = Arrays.stream(attributeNamesToConstructorParameterMapping).filter(e -> !e.isEmpty() && !isLocaleVariableName(e) && !this.annotation.hasAttribute(e)).toArray(String[]::new);
            if (invalidNames.length > 0) {
                this.elementWrapper.compilerMessage(this.annotation.unwrap()).asError().write(InternalValidationMessages.ERROR_BROKEN_VALIDATOR_ATTRIBUTE_NAME_MISMATCH, this.annotation.asElement().getSimpleName(), invalidNames);
                return false;
            }

           
            
            // loop over constructors and find if one is matching
            outer:
            for (ExecutableElementWrapper constructor : mappedTypeMirror.getTypeElement().get().getConstructors(Modifier.PUBLIC)) {

                if (constructor.getParameters().size() != attributeNamesToConstructorParameterMapping.length) {
                    continue;
                }

                int i = 0;
                for (String attributeName : attributeNamesToConstructorParameterMapping) {

                	if(!isLocaleVariableName(attributeName)) {
	                	TypeMirrorWrapper attribute;
	                	if (attributeName.isEmpty()) {
	                		
	                		// This will only work if annotated element is a method parameter
	                		if (!this.elementWrapper.isMethodParameter()) {
	                			this.elementWrapper.compilerMessage(annotation.unwrap()).asError().write(InternalValidationMessages.ERROR_BROKEN_VALIDATOR_INCORRECT_METHOD_PARAMETER_MAPPING);
	                	        return false;
	                		}
	                		
	                		attribute = this.elementWrapper.asType();
	                	} else {
	                	    attribute = this.annotation.getAttributeTypeMirror(attributeName).get();
	                	}
		                    
	                	if (!attribute.isAssignableTo(constructor.getParameters().get(i).asType())) {
	                		continue outer;
		                }
                	}
                    // next
                    i = i + 1;
                }

                // if this is reached, the we have found a matching constructor
                return true;
            }

            this.elementWrapper.compilerMessage(annotation.unwrap()).asError().write(InternalValidationMessages.ERROR_BROKEN_VALIDATOR_CONSTRUCTOR_PARAMETER_MAPPING, mappedTypeMirror.getSimpleName());
            return false;
        } else {
            // must have a noarg constructor or just the default
            TypeElementWrapper validatorImplTypeElement = mappedTypeMirror.getTypeElement().get();
            boolean hasNoargConstructor = validatorImplTypeElement.filterEnclosedElements().applyFilter(AptkCoreMatchers.IS_CONSTRUCTOR).applyFilter(AptkCoreMatchers.HAS_NO_PARAMETERS).getResult().size() == 1;
            boolean hasJustDefaultConstructor = validatorImplTypeElement.filterEnclosedElements().applyFilter(AptkCoreMatchers.IS_CONSTRUCTOR).hasSize(0);

            if (!(hasNoargConstructor || hasJustDefaultConstructor)) {
                this.elementWrapper.compilerMessage(annotation.unwrap()).asError().write(InternalValidationMessages.ERROR_BROKEN_VALIDATOR_MISSING_NOARG_CONSTRUCTOR, validatorImplTypeElement.getSimpleName());
                return false;
            }
        }
        
      

        return true;
    }


    /**
     * Creates the command needed to initialize an instance based on annotation configuration.
     * @return
     */
    public String createInstanceInitializationCommand() {
        StringBuilder stringBuilder = new StringBuilder();

        String genericTypeString = "";
        // Need to handle generic validator separately
        if (getValidatorAnnotation().mappedClass().getTypeElement().get().hasTypeParameters()) {
            TypeMirrorWrapper annotatedElementsTypeMirror = this.elementWrapper.asType();
            if (annotatedElementsTypeMirror.isCollection() || annotatedElementsTypeMirror.isIterable() || annotatedElementsTypeMirror.isArray()) {
                genericTypeString = "<" +annotatedElementsTypeMirror.getWrappedComponentType().getTypeDeclaration() + ">";
            } else {
                genericTypeString = "<" + annotatedElementsTypeMirror.getTypeDeclaration() + ">";
            }

        }

        stringBuilder
        	.append("new ").append(getValidatorAnnotation().mappedClass().getQualifiedName()).append(genericTypeString)
        	.append("(").append(getMappedAttributeValues()).append(")");
        
        return stringBuilder.toString();
    }
    
    /**
     * In some situations it'S necessary to get just the mapped attribute values as a colon separated string.
     * This is the case if there are additional constructor parameters not originated from annotated elements or annotation attributes.
     * @return a colon separated String of annotation attribute values than can be used for constructor calls
     */
    public String getMappedAttributeValues() {
    	  	
    	return Arrays.stream(getValidatorAnnotation().mappedAttributeNames())
			.<String>map( e -> {

	            if (e.isEmpty()) {
	            	return this.elementWrapper.getSimpleName();
	            } else if (isLocaleVariableName(e)) {
	            	return getLocalVariableName(e);
	        	} else {
	            	return getValidatorExpressionAttributeValueStringRepresentation(annotation.getAttributeWithDefault(e), annotation.getAttributeTypeMirror(e).get());
	            }
	    	})
    	.collect(Collectors.joining(", "));
    	

    }


    String getValidatorExpressionAttributeValueStringRepresentation(AnnotationValueWrapper annotationValueWrapper, TypeMirrorWrapper annotationAttributeTypeMirror) {

        if (annotationValueWrapper.isArray()) {
            return annotationValueWrapper.getArrayValue().stream().map(e -> getValidatorExpressionAttributeValueStringRepresentation(e, annotationAttributeTypeMirror.getWrappedComponentType())).collect(Collectors.joining(", ", "new " + annotationAttributeTypeMirror.getWrappedComponentType().getQualifiedName() + "[]{", "}"));
        } else if (annotationValueWrapper.isString()) {
            return "\"" + annotationValueWrapper.getStringValue() + "\"";
        } else if (annotationValueWrapper.isClass()) {
            return annotationValueWrapper.getClassValue().getQualifiedName() + ".class";
        } else if (annotationValueWrapper.isInteger()) {
            return annotationValueWrapper.getIntegerValue().toString();
        } else if (annotationValueWrapper.isLong()) {
            return annotationValueWrapper.getLongValue() + "L";
        } else if (annotationValueWrapper.isBoolean()) {
            return annotationValueWrapper.getBooleanValue().toString();
        } else if (annotationValueWrapper.isFloat()) {
            return annotationValueWrapper.getFloatValue() + "f";
        } else if (annotationValueWrapper.isDouble()) {
            return annotationValueWrapper.getDoubleValue().toString();
        } else if (annotationValueWrapper.isEnum()) {
            return TypeElementWrapper.toTypeElement(annotationValueWrapper.getEnumValue().getEnclosingElement().get()).getQualifiedName() + "." + annotationValueWrapper.getEnumValue().getSimpleName();
        } else {
            throw new IllegalStateException("Got unsupported annotation attribute type : USUALLY THIS CANNOT HAPPEN.");
        }

    }

    public String getStringRepresentationOfAnnotation() {
        return annotation.getStringRepresentation();
    }

}