package io.toolisticon.annotationprocessortoolkit.tools.validator;


import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

/**
 * Core Validators.
 * Convenience class that internally uses the {@link CoreMatchers} class to get the validators.
 * This might be a good candidate for code generation.
 */
public final class Validators {

    /**
     * Hidden constructor.
     */
    private Validators() {

    }

    /**
     * Validator to check if .
     */
    //public final static ImplicitValidator<Element, ImplicitMatcher<Element>> VALID_JAVA_PROPERTY_VALIDATOR = CoreMatchers.VALID_JAVA_PROPERTY.new ImplicitValidator<Element, ImplicitMatcher<Element>>(Matchers.VALID_JAVA_PROPERTY_MATCHER);

    /**
     * Validator to check if an Element matches one or none of the specific names.
     */
    public final static ExclusiveCharacteristicsElementValidator<Element, String, CharacteristicsMatcher<Element, String>> NAME_VALIDATOR = CoreMatchers.BY_NAME.getValidator();

    /**
     * Validator to check if an Element name matches regular expressions.
     */
    public final static InclusiveCharacteristicsElementValidator<Element, String, CharacteristicsMatcher<Element, String>> REGEX_NAME_VALIDATOR = CoreMatchers.BY_REGEX_NAME.getValidator();

    /**
     * Validator to check if an Element has annotations.
     */
    public final static InclusiveCharacteristicsElementValidator<Element, Class<? extends Annotation>, CharacteristicsMatcher<Element, Class<? extends Annotation>>> ANNOTATION_VALIDATOR = CoreMatchers.BY_ANNOTATION.getValidator();


    /**
     * Validator to check if an Element has annotations.
     */
    public final static ExclusiveCharacteristicsElementValidator<Element, ElementKind, CharacteristicsMatcher<Element, ElementKind>> ELEMENT_KIND_VALIDATOR = CoreMatchers.BY_ELEMENT_KIND.getValidator();

    /**
     * Validator to check if an Element has Modifiers.
     */
    public final static InclusiveCharacteristicsElementValidator<Element, Modifier, CharacteristicsMatcher<Element, Modifier>> MODIFIER_VALIDATOR = CoreMatchers.BY_MODIFIER.getValidator();


    /**
     * Validator to check if a TypeElement has a generic type.
     */
    public final static ExclusiveCharacteristicsElementValidator<Element, GenericType, CharacteristicsMatcher<Element, GenericType>> GENERIC_TYPE_VALIDATOR = CoreMatchers.BY_GENERIC_TYPE.getValidator();

    /**
     * Validator to check if a TypeElement has a generic type.
     */
    public final static ExclusiveCharacteristicsElementValidator<TypeElement, Class, CharacteristicsMatcher<TypeElement, Class>> RAW_TYPE_VALIDATOR = CoreMatchers.BY_RAW_TYPE.getValidator();


    /**
     * Validator to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCharacteristicsElementValidator<ExecutableElement, String[], CharacteristicsMatcher<ExecutableElement, String[]>> PARAMETER_TYPE_FQN_VALIDATOR = CoreMatchers.BY_PARAMETER_TYPE_FQN.getValidator();

    /**
     * Validator to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCharacteristicsElementValidator<ExecutableElement, Class[], CharacteristicsMatcher<ExecutableElement, Class[]>> PARAMETER_TYPE_VALIDATOR = CoreMatchers.BY_PARAMETER_TYPE.getValidator();


}
