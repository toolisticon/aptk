package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Convenience class to access all existing Matchers.
 */
public class Matchers {

    // all kinds of available matchers
    public final static Matcher<Class<? extends Annotation>> ANNOTATION_MATCHER = new Matcher<Class<? extends Annotation>>(new AnnotationMatcher());
    public final static Matcher<ElementKind> ELEMENT_KIND_MATCHER = new Matcher<ElementKind>(new ElementKindMatcher());
    public final static Matcher<Modifier> MODIFIER_MATCHER = new Matcher<Modifier>(new ModifierMatcher());
    public final static Matcher<String> NAME_MATCHER = new Matcher<String>(new NameMatcher());
    public final static Matcher<String> REGEX_NAME_MATCHER = new Matcher<String>(new RegexNameMatcher());

    public static Matcher<Class[]> PARAMETER_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<Class[]>(new ParameterExecutableMatcher(tools));
    }

    public static Matcher<String[]> PARAMETER_FQN_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<String[]>(new ParameterFQNExecutableMatcher(tools));
    }

    public static Matcher<Class> TYPE_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<Class>(new TypeMatcher(tools));
    }


}
