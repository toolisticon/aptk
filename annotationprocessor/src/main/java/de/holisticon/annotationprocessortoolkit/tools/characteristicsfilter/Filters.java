package de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validators;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericType;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Convenience class to access all existing filters.
 */
public class Filters {

    public final static Filter<Class<? extends Annotation>> ANNOTATION_FILTER = new Filter<Class<? extends Annotation>>(new GenericCharacteristicsFilter<Class<? extends Annotation>>(Validators.ANNOTATION_VALIDATOR));
    public final static Filter<ElementKind> ELEMENT_KIND_FILTER = new Filter<ElementKind>(new GenericCharacteristicsFilter<ElementKind>(Validators.ELEMENT_KIND_VALIDATOR));
    public final static Filter<Modifier> MODIFIER_FILTER = new Filter<Modifier>(new GenericCharacteristicsFilter<Modifier>(Validators.MODIFIER_VALIDATOR));
    public final static Filter<String> NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validators.NAME_VALIDATOR));
    public final static Filter<String> REGEX_NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validators.REGEX_NAME_VALIDATOR));


    public static Filter<Class[]> PARAMETER_FILTER(FrameworkToolWrapper tools) {
        return new Filter<Class[]>(new GenericCharacteristicsFilter<Class[]>(Validators.PARAMETER_VALIDATOR(tools)));
    }

    public static Filter<String[]> PARAMETER_FQN_FILTER(FrameworkToolWrapper tools) {
        return new Filter<String[]>(new GenericCharacteristicsFilter<String[]>(Validators.PARAMETER_FQN_VALIDATOR(tools)));
    }

    public static Filter<Class> RAW_TYPE_FILTER(FrameworkToolWrapper tools) {
        return new Filter<Class>(new GenericCharacteristicsFilter<Class>(Validators.RAW_TYPE_VALIDATOR(tools)));
    }

    public static Filter<GenericType> GENERIC_TYPE_FILTER(FrameworkToolWrapper tools) {
        return new Filter<GenericType>(new GenericCharacteristicsFilter<GenericType>(Validators.GENERIC_TYPE_VALIDATOR(tools)));
    }


}
