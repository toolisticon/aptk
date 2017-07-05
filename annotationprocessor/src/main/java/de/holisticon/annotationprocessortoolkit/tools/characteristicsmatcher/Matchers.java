package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericType;

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

    public static Matcher<Class[]> PARAMETER_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<Class[]>(new ParameterExecutableMatcher(tools));
    }

    public static Matcher<String[]> PARAMETER_FQN_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<String[]>(new ParameterFQNExecutableMatcher(tools));
    }

    public static Matcher<Class> RAW_TYPE_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<Class>(new RawTypeMatcher(tools));
    }

    public static Matcher<GenericType> GENERIC_TYPE_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<GenericType>(new GenericTypeMatcher(tools));
    }


}
