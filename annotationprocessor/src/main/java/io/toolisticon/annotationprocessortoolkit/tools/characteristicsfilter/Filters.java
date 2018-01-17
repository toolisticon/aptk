package io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter;


import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validators;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Convenience class to access all existing filters.
 */
public final class Filters {

    /**
     * Hidden constructor.
     */
    private Filters() {

    }


    public static final Filter<Class<? extends Annotation>> ANNOTATION_FILTER =
            new Filter<Class<? extends Annotation>>(new GenericCharacteristicsFilter<Class<? extends Annotation>>(Validators.ANNOTATION_VALIDATOR));

    public static final Filter<ElementKind> ELEMENT_KIND_FILTER =
            new Filter<ElementKind>(new GenericCharacteristicsFilter<ElementKind>(Validators.ELEMENT_KIND_VALIDATOR));

    public static final Filter<Modifier> MODIFIER_FILTER = new Filter<Modifier>(new GenericCharacteristicsFilter<Modifier>(Validators.MODIFIER_VALIDATOR));

    public static final Filter<String> NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validators.NAME_VALIDATOR));

    public static final Filter<String> REGEX_NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validators.REGEX_NAME_VALIDATOR));

    public static final Filter<Class[]> PARAMETER_TYPE_FILTER = new Filter<Class[]>(new GenericCharacteristicsFilter<Class[]>(Validators.PARAMETER_TYPE_VALIDATOR));

    public static final Filter<String[]> PARAMETER_TYPE_FQN_FILTER = new Filter<String[]>(new GenericCharacteristicsFilter<String[]>(Validators.PARAMETER_TYPE_FQN_VALIDATOR));

    public static final Filter<Class> RAW_TYPE_FILTER = new Filter<Class>(new GenericCharacteristicsFilter<Class>(Validators.RAW_TYPE_VALIDATOR));

    public static final Filter<GenericType> GENERIC_TYPE_FILTER = new Filter<GenericType>(new GenericCharacteristicsFilter<GenericType>(Validators.GENERIC_TYPE_VAIDATOR));


}
