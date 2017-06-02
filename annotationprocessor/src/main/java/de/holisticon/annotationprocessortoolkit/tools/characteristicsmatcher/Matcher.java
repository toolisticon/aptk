package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Convenient method that offers access to all existing characteristic matchers.
 */
public class Matcher<T> {

    // all kinds of available matchers
    public final static Matcher<Class<? extends Annotation>> ANNOTATION_MATCHER = new Matcher<Class<? extends Annotation>>(new AnnotationElementCharacteristicMatcher());
    public final static Matcher<ElementKind> ELEMENT_KIND_MATCHER = new Matcher<ElementKind>(new ElementKindElementcharacteristicMatcher());
    public final static Matcher<Modifier> MODIFIER_MATCHER = new Matcher<Modifier>(new ModifierElementCharacteristicMatcher());
    public final static Matcher<String> NAME_MATCHER = new Matcher<String>(new NameElementCharacteristicMatcher());
    public final static Matcher<String> REGEX_NAME_MATCHER = new Matcher<String>(new RegexNameElementCharacteristicMatcher());

    public static Matcher<Class[]> PARAMETER_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<Class[]>(new ParameterExecutableElementCharacteristicMatcher(tools));
    }

    public static Matcher<String[]> PARAMETER_FQN_MATCHER(FrameworkToolWrapper tools) {
        return new Matcher<String[]>(new ParameterFQNExecutableElementCharacteristicMatcher(tools));
    }


    private final GenericElementCharacteristicMatcher<T> matcher;


    /**
     * Hidden constructor.
     */
    private Matcher(GenericElementCharacteristicMatcher<T> matcher) {
        this.matcher = matcher;
    }

    /**
     * Hidden constructor.
     */
    private Matcher() {
        this.matcher = null;
    }

    public GenericElementCharacteristicMatcher<T> getMatcher() {
        return this.matcher;
    }


}
