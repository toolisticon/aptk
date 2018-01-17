package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Convenience class to access all existing Matchers.
 */
public final class Matchers {

    /**
     * Hidden constructor.
     */
    private Matchers() {

    }

    // all kinds of available matchers
    public static final Matcher<Class<? extends Annotation>> ANNOTATION_MATCHER =
            new Matcher<Class<? extends Annotation>>(new AnnotationMatcher());

    public static final Matcher<ElementKind> ELEMENT_KIND_MATCHER = new Matcher<ElementKind>(new ElementKindMatcher());

    public static final Matcher<Modifier> MODIFIER_MATCHER = new Matcher<Modifier>(new ModifierMatcher());

    public static final Matcher<String> NAME_MATCHER = new Matcher<String>(new NameMatcher());

    public static final Matcher<String> REGEX_NAME_MATCHER = new Matcher<String>(new RegexNameMatcher());

    public static final Matcher<Class[]> PARAMETER_TYPE_MATCHER = new Matcher<Class[]>(new ParameterExecutableMatcher());


    public static final Matcher<String[]> PARAMETER_TYPE_FQN_MATCHER = new Matcher<String[]>(new ParameterFQNExecutableMatcher());

    public static final Matcher<Class> RAW_TYPE_MATCHER = new Matcher<Class>(new RawTypeMatcher());

    public static final Matcher<GenericType> GENERIC_TYPE_MATCHER = new Matcher<GenericType>(new GenericTypeMatcher());


}
