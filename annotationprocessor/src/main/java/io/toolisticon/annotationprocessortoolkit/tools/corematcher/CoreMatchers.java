package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByAnnotationMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByElementKindMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByGenericTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByModifierMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNameMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNameRegexMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeFqnMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByRawTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.HasVoidReturnTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsAnnotationTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsAssignableToMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsClassMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsConstructorMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsEnumMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsExecutableElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsFieldMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsInterfaceMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsMethodMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsPackageElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsPackageMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsParameterMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsTypeElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsVariableElementMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;

/**
 * Core matchers provided by the annotation processor toolkit which can be used for validation and filtering.
 * This can be done in if statements or via the fluent apis for filtering and validation: {@link io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter}
 * and {@link io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator}
 */
public class CoreMatchers {

    private CoreMatchers() {

    }

    // ---------------------------------------------------------------------------------
    // -- CHARACTERISTICS CORE MATCHER
    // ---------------------------------------------------------------------------------


    /**
     * Matcher to check if an Element has a specific name.
     */
    public final static ExclusiveCharacteristicElementBasedCoreMatcher<String> BY_NAME = new ExclusiveCharacteristicElementBasedCoreMatcher<String>(new ByNameMatcher(), CoreMatcherValidationMessages.BY_NAME);

    /**
     * Matcher to check if an Element name matches a specific regular expression.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<String> BY_REGEX_NAME = new InclusiveCharacteristicElementBasedCoreMatcher<String>(new ByNameRegexMatcher(), CoreMatcherValidationMessages.BY_NAME_REGEX);


    /**
     * Matcher to check if an Element is annotated with a specific annotation.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<Class<? extends Annotation>> BY_ANNOTATION = new InclusiveCharacteristicElementBasedCoreMatcher<Class<? extends Annotation>>(new ByAnnotationMatcher(), CoreMatcherValidationMessages.BY_ANNOTATION);

    /**
     * Matcher to check if an Element is of a specific ElementKind.
     */
    public final static ExclusiveCharacteristicElementBasedCoreMatcher<ElementKind> BY_ELEMENT_KIND = new ExclusiveCharacteristicElementBasedCoreMatcher<ElementKind>(new ByElementKindMatcher(), CoreMatcherValidationMessages.BY_ELEMENT_KIND);

    /**
     * Matcher to check if an Element has a specific Modifier.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<Modifier> BY_MODIFIER = new InclusiveCharacteristicElementBasedCoreMatcher<Modifier>(new ByModifierMatcher(), CoreMatcherValidationMessages.BY_MODIFIER);


    /**
     * Matcher to check if an TypeElement matches a specific generic type.
     */
    public final static ExclusiveCharacteristicElementBasedCoreMatcher<GenericType> BY_GENERIC_TYPE = new ExclusiveCharacteristicElementBasedCoreMatcher<GenericType>(new ByGenericTypeMatcher(), CoreMatcherValidationMessages.BY_GENERIC_TYPE);

    /**
     * Matcher to check if an TypeElement matches a specific generic type.
     */
    public final static ExclusiveCharacteristicCoreMatcher<TypeElement, Class> BY_RAW_TYPE = new ExclusiveCharacteristicCoreMatcher<TypeElement, Class>(new ByRawTypeMatcher(), CoreMatcherValidationMessages.BY_RAW_TYPE);


    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCharacteristicCoreMatcher<ExecutableElement, String[]> BY_PARAMETER_TYPE_FQN = new ExclusiveCharacteristicCoreMatcher<ExecutableElement, String[]>(new ByParameterTypeFqnMatcher(), CoreMatcherValidationMessages.BY_PARAMETER_TYPE_FQN);

    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCharacteristicCoreMatcher<ExecutableElement, Class[]> BY_PARAMETER_TYPE = new ExclusiveCharacteristicCoreMatcher<ExecutableElement, Class[]>(new ByParameterTypeMatcher(), CoreMatcherValidationMessages.BY_PARAMETER_TYPE);

    /**
     * Matcher to check if an Element is assignable to a specific type
     */
    public final static ExclusiveCharacteristicElementBasedCoreMatcher<Class> IS_ASSIGNABLE_TO = new ExclusiveCharacteristicElementBasedCoreMatcher<Class>(new IsAssignableToMatcher(), CoreMatcherValidationMessages.IS_ASSIGNABLE_TO);


    // ---------------------------------------------------------------------------------
    // -- IMPLICIT CORE MATCHER
    // ---------------------------------------------------------------------------------

    public final static ImplicitCoreMatcher<ExecutableElement> HAS_VOID_RETURN_TYPE = new ImplicitCoreMatcher<ExecutableElement>(new HasVoidReturnTypeMatcher(), CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE);

    // ---------------------------------------------------------------------------------
    // -- IS CORE MATCHER
    // ---------------------------------------------------------------------------------

    /**
     * Matcher to check if passed element is a ExecutableElement.
     */
    public final static IsCoreMatcher<Element, ExecutableElement> IS_EXECUTABLE_ELEMENT = new IsCoreMatcher<Element, ExecutableElement>(new IsExecutableElementMatcher<Element>(), CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT);

    /**
     * Matcher to check if passed element is a TypeElement.
     */
    public final static IsCoreMatcher<Element, TypeElement> IS_TYPE_ELEMENT = new IsCoreMatcher<Element, TypeElement>(new IsTypeElementMatcher<Element>(), CoreMatcherValidationMessages.IS_TYPE_ELEMENT);

    /**
     * Matcher to check if passed element is a VariableElement.
     */
    public final static IsCoreMatcher<Element, VariableElement> IS_VARIABLE_ELEMENT = new IsCoreMatcher<Element, VariableElement>(new IsVariableElementMatcher<Element>(), CoreMatcherValidationMessages.IS_VARIABLE_ELEMENT);

    /**
     * Matcher to check if passed element is a PackageElement.
     */
    public final static IsCoreMatcher<Element, PackageElement> IS_PACKAGE_ELEMENT = new IsCoreMatcher<Element, PackageElement>(new IsPackageElementMatcher<Element>(), CoreMatcherValidationMessages.IS_PACKAGE_ELEMENT);

    /**
     * Matcher to check if passed element represents a package.
     */
    public final static IsCoreMatcher<Element, PackageElement> IS_PACKAGE = new IsCoreMatcher<Element, PackageElement>(new IsPackageMatcher<Element>(), CoreMatcherValidationMessages.IS_PACKAGE);

    /**
     * Matcher to check if passed element represents a class.
     */
    public final static IsCoreMatcher<Element, TypeElement> IS_CLASS = new IsCoreMatcher<Element, TypeElement>(new IsClassMatcher<Element>(), CoreMatcherValidationMessages.IS_CLASS);

    /**
     * Matcher to check if passed element represents an enum.
     */
    public final static IsCoreMatcher<Element, TypeElement> IS_ENUM = new IsCoreMatcher<Element, TypeElement>(new IsEnumMatcher<Element>(), CoreMatcherValidationMessages.IS_ENUM);

    /**
     * Matcher to check if passed element represents an interface.
     */
    public final static IsCoreMatcher<Element, TypeElement> IS_INTERFACE = new IsCoreMatcher<Element, TypeElement>(new IsInterfaceMatcher<Element>(), CoreMatcherValidationMessages.IS_INTERFACE);

    /**
     * Matcher to check if passed element represents a method.
     */
    public final static IsCoreMatcher<Element, ExecutableElement> IS_METHOD = new IsCoreMatcher<Element, ExecutableElement>(new IsMethodMatcher<Element>(), CoreMatcherValidationMessages.IS_METHOD);

    /**
     * Matcher to check if passed element represents a constructor.
     */
    public final static IsCoreMatcher<Element, ExecutableElement> IS_CONSTRUCTOR = new IsCoreMatcher<Element, ExecutableElement>(new IsConstructorMatcher<Element>(), CoreMatcherValidationMessages.IS_CONSTRUCTOR);

    /**
     * Matcher to check if passed element represents an annotation type.
     */
    public final static IsCoreMatcher<Element, TypeElement> IS_ANNOTATION_TYPE = new IsCoreMatcher<Element, TypeElement>(new IsAnnotationTypeMatcher<Element>(), CoreMatcherValidationMessages.IS_ANNOTATION_TYPE);

    /**
     * Matcher to check if passed element represents a field.
     */
    public final static IsCoreMatcher<Element, VariableElement> IS_FIELD = new IsCoreMatcher<Element, VariableElement>(new IsFieldMatcher<Element>(), CoreMatcherValidationMessages.IS_FIELD);

    /**
     * Matcher to check if passed element represents a parameter.
     */
    public final static IsCoreMatcher<Element, VariableElement> IS_PARAMETER = new IsCoreMatcher<Element, VariableElement>(new IsParameterMatcher<Element>(), CoreMatcherValidationMessages.IS_PARAMETER);


}
