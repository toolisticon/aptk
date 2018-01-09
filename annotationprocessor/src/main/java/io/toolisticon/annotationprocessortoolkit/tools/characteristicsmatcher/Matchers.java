package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
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
    private static final Matcher<Class<? extends Annotation>> ANNOTATION_MATCHER =
            new Matcher<Class<? extends Annotation>>(new AnnotationMatcher());
    private static final Matcher<ElementKind> ELEMENT_KIND_MATCHER = new Matcher<ElementKind>(new ElementKindMatcher());
    private static final Matcher<Modifier> MODIFIER_MATCHER = new Matcher<Modifier>(new ModifierMatcher());
    private static final Matcher<String> NAME_MATCHER = new Matcher<String>(new NameMatcher());
    private static final Matcher<String> REGEX_NAME_MATCHER = new Matcher<String>(new RegexNameMatcher());

    public static Matcher<Class<? extends Annotation>> getAnnotationMatcher() {
        return ANNOTATION_MATCHER;
    }

    public static Matcher<ElementKind> getElementKindMatcher() {
        return ELEMENT_KIND_MATCHER;
    }

    public static Matcher<Modifier> getModifierMatcher() {
        return MODIFIER_MATCHER;
    }

    public static Matcher<String> getNameMatcher() {
        return NAME_MATCHER;
    }

    public static Matcher<String> getRegexNameMatcher() {
        return REGEX_NAME_MATCHER;
    }

    public static Matcher<Class[]> getParameterMatcher(FrameworkToolWrapper tools) {
        return new Matcher<Class[]>(new ParameterExecutableMatcher(tools));
    }

    public static Matcher<String[]> getParameterFqnMatcher(FrameworkToolWrapper tools) {
        return new Matcher<String[]>(new ParameterFQNExecutableMatcher(tools));
    }

    public static Matcher<Class> getRawTypeMatcher(FrameworkToolWrapper tools) {
        return new Matcher<Class>(new RawTypeMatcher(tools));
    }

    public static Matcher<GenericType> getGenericTypeMatcher(FrameworkToolWrapper tools) {
        return new Matcher<GenericType>(new GenericTypeMatcher(tools));
    }


}
