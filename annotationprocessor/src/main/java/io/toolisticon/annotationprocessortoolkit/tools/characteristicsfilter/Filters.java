package io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
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


    private static final Filter<Class<? extends Annotation>> ANNOTATION_FILTER =
            new Filter<Class<? extends Annotation>>(new GenericCharacteristicsFilter<Class<? extends Annotation>>(Validators.getAnnotationValidator()));
    private static final Filter<ElementKind> ELEMENT_KIND_FILTER =
            new Filter<ElementKind>(new GenericCharacteristicsFilter<ElementKind>(Validators.getElementKindValidator()));
    private static final Filter<Modifier> MODIFIER_FILTER = new Filter<Modifier>(new GenericCharacteristicsFilter<Modifier>(Validators.getModifierValidator()));
    private static final Filter<String> NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validators.getNameValidator()));
    private static final Filter<String> REGEX_NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validators.getRegexNameValidator()));


    public static Filter<Class<? extends Annotation>> getAnnotationFilter() {
        return ANNOTATION_FILTER;
    }

    public static Filter<ElementKind> getElementKindFilter() {
        return ELEMENT_KIND_FILTER;
    }

    public static Filter<Modifier> getModifierFilter() {
        return MODIFIER_FILTER;
    }

    public static Filter<String> getNameFilter() {
        return NAME_FILTER;
    }

    public static Filter<String> getRegexNameFilter() {
        return REGEX_NAME_FILTER;
    }

    public static Filter<Class[]> getParameterFilter(FrameworkToolWrapper tools) {
        return new Filter<Class[]>(new GenericCharacteristicsFilter<Class[]>(Validators.getParameterValidator(tools)));
    }

    public static Filter<String[]> getParameterFqnFilter(FrameworkToolWrapper tools) {
        return new Filter<String[]>(new GenericCharacteristicsFilter<String[]>(Validators.getParameterFqnValidator(tools)));
    }

    public static Filter<Class> getRawTypeFilter(FrameworkToolWrapper tools) {
        return new Filter<Class>(new GenericCharacteristicsFilter<Class>(Validators.getRawTypeValidator(tools)));
    }

    public static Filter<GenericType> getGenericTypeFilter(FrameworkToolWrapper tools) {
        return new Filter<GenericType>(new GenericCharacteristicsFilter<GenericType>(Validators.getGenericTypeValidator(tools)));
    }


}
