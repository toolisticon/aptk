package de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validator;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Filter enum
 */
public class Filter<T> {

    public final static Filter<Class<? extends Annotation>> ANNOTATION_FILTER = new Filter<Class<? extends Annotation>>(new GenericCharacteristicsFilter<Class<? extends Annotation>>(Validator.ANNOTATION_VALIDATOR));
    public final static Filter<ElementKind> ELEMENT_KIND_FILTER = new Filter<ElementKind>(new GenericCharacteristicsFilter<ElementKind>(Validator.ELEMENT_KIND_VALIDATOR));
    public final static Filter<Modifier> MODIFIER_FILTER = new Filter<Modifier>(new GenericCharacteristicsFilter<Modifier>(Validator.MODIFIER_VALIDATOR));
    public final static Filter<String> NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validator.NAME_VALIDATOR));
    public final static Filter<String> REGEX_NAME_FILTER = new Filter<String>(new GenericCharacteristicsFilter<String>(Validator.REGEX_NAME_VALIDATOR));


    private final GenericCharacteristicsFilter<T> filter;

    /**
     * Hidden constructor.
     */
    private Filter(GenericCharacteristicsFilter<T> filter) {
        this.filter = filter;
    }

    public GenericCharacteristicsFilter<T> getFilter() {
        return filter;
    }


}
