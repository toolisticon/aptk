package io.toolisticon.annotationprocessortoolkit.tools.matcher;

import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByAnnotationMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByElementKindMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByGenericTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeFqnMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsExecutableElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByModifierMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNameMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByRawTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNameRegexMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

/**
 * Core matchers provided by the toolkit.
 */
public final class Matchers {

    private Matchers() {

    }

    /**
     * Matcher to check if an Element has a specific name.
     */
    public final static CharacteristicsMatcher<Element, String> NAME_MATCHER = new ByNameMatcher();

    /**
     * Matcher to check if an Element name matches a specific regular expression.
     */
    public final static CharacteristicsMatcher<Element, String> REGEX_NAME_MATCHER = new ByNameRegexMatcher();


    /**
     * Matcher to check if an Element is annotated with a specific annotation.
     */
    public final static CharacteristicsMatcher<Element, Class<? extends Annotation>> ANNOTATION_MATCHER = new ByAnnotationMatcher();

    /**
     * Matcher to check if an Element is of a specific ElementKind.
     */
    public final static CharacteristicsMatcher<Element, ElementKind> ELEMENT_KIND_MATCHER = new ByElementKindMatcher();

    /**
     * Matcher to check if an Element has a specific Modifier.
     */
    public final static CharacteristicsMatcher<Element, Modifier> MODIFIER_MATCHER = new ByModifierMatcher();


    /**
     * Matcher to check if an TypeElement matches a specific generic type.
     */
    public final static CharacteristicsMatcher<Element, GenericType> GENERIC_TYPE_MATCHER = new ByGenericTypeMatcher();

    /**
     * Matcher to check if an TypeElement matches a specific generic type.
     */
    public final static CharacteristicsMatcher<TypeElement, Class> RAW_TYPE_MATCHER = new ByRawTypeMatcher();


    /**
     * Matcher to check if an ExecutableElement has specific parameter types.
     */
    public final static CharacteristicsMatcher<ExecutableElement, String[]> PARAMETER_TYPE_FQN_MATCHER = new ByParameterTypeFqnMatcher();

    /**
     * Matcher to check if an ExecutableElement has specific parameter types.
     */
    public final static CharacteristicsMatcher<ExecutableElement, Class[]> PARAMETER_TYPE_MATCHER = new ByParameterTypeMatcher();


    // ---------------------------------------------------------------------------------
    // -- IS MATCHER
    // ---------------------------------------------------------------------------------


    /**
     * Matcher to check if passed Element is an ExecutableElement.
     */
    public final static ImplicitMatcher<Element> IS_EXECUTABLE_ELEMENT = new IsExecutableElementMatcher<Element>();


}
