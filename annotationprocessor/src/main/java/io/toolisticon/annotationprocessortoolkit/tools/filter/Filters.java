package io.toolisticon.annotationprocessortoolkit.tools.filter;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCharacteristicsElementValidator;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

/**
 * Core Filters.
 * Convenience class that internally uses the {@link CoreMatchers} class to get the filters.
 * This might be a good candidate for code generation.
 */
public class Filters {

    /**
     * Hidden constructor.
     */
    private Filters() {

    }

    /**
     * Validator to check if .
     */
    //public final static ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> VALID_JAVA_PROPERTY_FILTER = CoreMatchers.VALID_JAVA_PROPERTY.new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(Validators.VALID_JAVA_PROPERTY_VALIDATOR);

    /**
     * Validator to check if an Element matches one or none of the specific names.
     */
    public final static ExclusiveCharacteristicsElementFilter<Element, String, ExclusiveCharacteristicsElementValidator<Element, String, CharacteristicsMatcher<Element, String>>> NAME_FILTER = CoreMatchers.BY_NAME.getFilter();

    /**
     * Validator to check if an Element name matches regular expressions.
     */
    public final static InclusiveCharacteristicsElementFilter<Element, String, InclusiveCharacteristicsElementValidator<Element, String, CharacteristicsMatcher<Element, String>>> REGEX_NAME_FILTER = CoreMatchers.BY_REGEX_NAME.getFilter();

    /**
     * Validator to check if an Element has annotations.
     */
    public final static InclusiveCharacteristicsElementFilter<Element, Class<? extends Annotation>, InclusiveCharacteristicsElementValidator<Element, Class<? extends Annotation>, CharacteristicsMatcher<Element, Class<? extends Annotation>>>> ANNOTATION_FILTER = CoreMatchers.BY_ANNOTATION.getFilter();


    /**
     * Validator to check if an Element has annotations.
     */
    public final static ExclusiveCharacteristicsElementFilter<Element, ElementKind, ExclusiveCharacteristicsElementValidator<Element, ElementKind, CharacteristicsMatcher<Element, ElementKind>>> ELEMENT_KIND_FILTER = CoreMatchers.BY_ELEMENT_KIND.getFilter();

    /**
     * Validator to check if an Element has Modifiers.
     */
    public final static InclusiveCharacteristicsElementFilter<Element, Modifier, InclusiveCharacteristicsElementValidator<Element, Modifier, CharacteristicsMatcher<Element, Modifier>>> MODIFIER_FILTER = CoreMatchers.BY_MODIFIER.getFilter();


    /**
     * Validator to check if a TypeElement has a generic type.
     */
    public final static ExclusiveCharacteristicsElementFilter<Element, GenericType, ExclusiveCharacteristicsElementValidator<Element, GenericType, CharacteristicsMatcher<Element, GenericType>>> GENERIC_TYPE_FILTER = CoreMatchers.BY_GENERIC_TYPE.getFilter();

    /**
     * Validator to check if a TypeElement has a generic type.
     */
    public final static ExclusiveCharacteristicsElementFilter<TypeElement, Class, ExclusiveCharacteristicsElementValidator<TypeElement, Class, CharacteristicsMatcher<TypeElement, Class>>> RAW_TYPE_FILTER = CoreMatchers.BY_RAW_TYPE.getFilter();


    /**
     * Validator to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCharacteristicsElementFilter<ExecutableElement, String[], ExclusiveCharacteristicsElementValidator<ExecutableElement, String[], CharacteristicsMatcher<ExecutableElement, String[]>>> PARAMETER_TYPE_FQN_FILTER = CoreMatchers.BY_PARAMETER_TYPE_FQN.getFilter();

    /**
     * Validator to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCharacteristicsElementFilter<ExecutableElement, Class[], ExclusiveCharacteristicsElementValidator<ExecutableElement, Class[], CharacteristicsMatcher<ExecutableElement, Class[]>>> PARAMETER_TYPE_FILTER = CoreMatchers.BY_PARAMETER_TYPE.getFilter();


}
