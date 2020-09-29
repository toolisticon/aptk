package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByAnnotationMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByElementKindMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByGenericTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByModifierMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNameMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNameRegexMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByNumberOfParametersMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeFqnMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByParameterTypeMirrorMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByQualifiedNameMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByQualifiedNameRegexMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByRawTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByReturnTypeFqnMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByReturnTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByReturnTypeMirrorMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.HasNoParametersMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.HasNoThrownTypesMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.HasPublicNoargConstructorMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.HasVoidReturnTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsAnnotationTypeMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsAssignableToMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsAttributeFieldMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsClassMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsConstructorMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsEnumMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsExecutableElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsFieldMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsGetterMethodMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsInterfaceMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsMethodMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsPackageElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsPackageMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsParameterMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsSetterMethodMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsTypeElementMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsTypeEqualMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.IsVariableElementMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
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
    public final static ExclusiveCriteriaElementBasedCoreMatcher<String> BY_NAME = new ExclusiveCriteriaElementBasedCoreMatcher<>(new ByNameMatcher(), CoreMatcherValidationMessages.BY_NAME);

    /**
     * Matcher to check if an Element name matches a specific regular expression.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<String> BY_REGEX_NAME = new InclusiveCharacteristicElementBasedCoreMatcher<>(new ByNameRegexMatcher(), CoreMatcherValidationMessages.BY_NAME_REGEX);

    /**
     * Matcher to check if an Element has a specific name.
     */
    public final static ExclusiveCriteriaElementBasedCoreMatcher<String> BY_QUALIFIED_NAME = new ExclusiveCriteriaElementBasedCoreMatcher<>(new ByQualifiedNameMatcher(), CoreMatcherValidationMessages.BY_QUALIFIED_NAME);

    /**
     * Matcher to check if an Element name matches a specific regular expression.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<String> BY_REGEX_QUALIFIED_NAME = new InclusiveCharacteristicElementBasedCoreMatcher<>(new ByQualifiedNameRegexMatcher(), CoreMatcherValidationMessages.BY_QUALIFIED_NAME_REGEX);



    /**
     * Matcher to check if an Element is annotated with a specific annotation.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<Class<? extends Annotation>> BY_ANNOTATION = new InclusiveCharacteristicElementBasedCoreMatcher<>(new ByAnnotationMatcher(), CoreMatcherValidationMessages.BY_ANNOTATION);

    /**
     * Matcher to check if an Element is of a specific ElementKind.
     */
    public final static ExclusiveCriteriaElementBasedCoreMatcher<ElementKind> BY_ELEMENT_KIND = new ExclusiveCriteriaElementBasedCoreMatcher<>(new ByElementKindMatcher(), CoreMatcherValidationMessages.BY_ELEMENT_KIND);

    /**
     * Matcher to check if an Element has a specific Modifier.
     */
    public final static InclusiveCharacteristicElementBasedCoreMatcher<Modifier> BY_MODIFIER = new InclusiveCharacteristicElementBasedCoreMatcher<>(new ByModifierMatcher(), CoreMatcherValidationMessages.BY_MODIFIER);


    /**
     * Matcher to check if an TypeElement matches a specific generic type.
     */
    public final static ExclusiveCriteriaElementBasedCoreMatcher<GenericType> BY_GENERIC_TYPE = new ExclusiveCriteriaElementBasedCoreMatcher<>(new ByGenericTypeMatcher(), CoreMatcherValidationMessages.BY_GENERIC_TYPE);

    /**
     * Matcher to check if an TypeElement matches a specific generic type.
     */
    public final static ExclusiveCriteriaElementBasedCoreMatcher<Class> BY_RAW_TYPE = new ExclusiveCriteriaElementBasedCoreMatcher<>(new ByRawTypeMatcher(), CoreMatcherValidationMessages.BY_RAW_TYPE);


    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, String[]> BY_PARAMETER_TYPE_FQN = new ExclusiveCriteriaCoreMatcher<>(new ByParameterTypeFqnMatcher(), CoreMatcherValidationMessages.BY_PARAMETER_TYPE_FQN);

    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, Class[]> BY_PARAMETER_TYPE = new ExclusiveCriteriaCoreMatcher<>(new ByParameterTypeMatcher(), CoreMatcherValidationMessages.BY_PARAMETER_TYPE);

    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, TypeMirror[]> BY_PARAMETER_TYPE_MIRROR = new ExclusiveCriteriaCoreMatcher<>(new ByParameterTypeMirrorMatcher(), CoreMatcherValidationMessages.BY_PARAMETER_TYPE_MIRROR);

    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, Integer> BY_NUMBER_OF_PARAMETERS = new ExclusiveCriteriaCoreMatcher<>(new ByNumberOfParametersMatcher(), CoreMatcherValidationMessages.BY_NUMBER_OF_PARAMETERS);


    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, String> BY_RETURN_TYPE_FQN = new ExclusiveCriteriaCoreMatcher<>(new ByReturnTypeFqnMatcher(), CoreMatcherValidationMessages.BY_RETURN_TYPE_FQN);

    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, Class> BY_RETURN_TYPE = new ExclusiveCriteriaCoreMatcher<>(new ByReturnTypeMatcher(), CoreMatcherValidationMessages.BY_RETURN_TYPE);

    /**
     * Matcher to check if an ExecutableElement has specific parameter types
     */
    public final static ExclusiveCriteriaCoreMatcher<ExecutableElement, TypeMirror> BY_RETURN_TYPE_MIRROR = new ExclusiveCriteriaCoreMatcher<>(new ByReturnTypeMirrorMatcher(), CoreMatcherValidationMessages.BY_RETURN_TYPE_MIRROR);



    /**
     * Matcher to check if an Element is assignable to a specific type
     */
    public final static ExclusiveCriteriaElementBasedCoreMatcher<Class> IS_ASSIGNABLE_TO = new ExclusiveCriteriaElementBasedCoreMatcher<>(new IsAssignableToMatcher(), CoreMatcherValidationMessages.IS_ASSIGNABLE_TO);

    /**
     * Matcher to check if an Element is assignable to a specific type
     */
    public final static ExclusiveCriteriaElementBasedCoreMatcher<Class> IS_TYPE_EQUAL = new ExclusiveCriteriaElementBasedCoreMatcher<>(new IsTypeEqualMatcher(), CoreMatcherValidationMessages.IS_TYPE_EQUAL);


    // ---------------------------------------------------------------------------------
    // -- IMPLICIT CORE MATCHER
    // ---------------------------------------------------------------------------------

    /**
     * Matcher to check if an ExecutableElement has a void return type
     */
    public final static ImplicitCoreMatcher<ExecutableElement> HAS_VOID_RETURN_TYPE = new ImplicitCoreMatcher<>(new HasVoidReturnTypeMatcher(), CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE);

    /**
     * Matcher to check if an ExecutableElement takes no parameter
     */
    public final static ImplicitCoreMatcher<ExecutableElement> HAS_NO_PARAMETERS = new ImplicitCoreMatcher<>(new HasNoParametersMatcher(), CoreMatcherValidationMessages.HAS_NO_PARAMETERS);

    /**
     * Matcher to check if an ExecutableElement has no thrown types
     */
    public final static ImplicitCoreMatcher<ExecutableElement> HAS_NO_THROWN_TYPES = new ImplicitCoreMatcher<>(new HasNoThrownTypesMatcher(), CoreMatcherValidationMessages.HAS_NO_THROWN_TYPES);


    /**
     * Matcher to check if an TypeElement has a void return type
     */
    public final static ImplicitElementBasedCoreMatcher HAS_PUBLIC_NOARG_CONSTRUCTOR = new ImplicitElementBasedCoreMatcher(new HasPublicNoargConstructorMatcher(), CoreMatcherValidationMessages.HAS_PUBLIC_NOARG_CONSTRUCTOR);

    /**
     * Matcher to check if an TypeElement has a void return type
     */
    public final static ImplicitCoreMatcher<ExecutableElement> IS_GETTER_METHOD = new ImplicitCoreMatcher<>(new IsGetterMethodMatcher(), CoreMatcherValidationMessages.IS_GETTER_METHOD);

    /**
     * Matcher to check if an TypeElement has a void return type
     */
    public final static ImplicitCoreMatcher<ExecutableElement> IS_SETTER_METHOD = new ImplicitCoreMatcher<>(new IsSetterMethodMatcher(), CoreMatcherValidationMessages.IS_SETTER_METHOD);

    /**
     * Matcher to check if passed element represents an attribute.
     * Passed VariableElement for field must not be static and must have valid getter and setter methods.
     */
    public final static ImplicitCoreMatcher<VariableElement> IS_ATTRIBUTE_FIELD = new ImplicitCoreMatcher<>(new IsAttributeFieldMatcher(), CoreMatcherValidationMessages.IS_ATTRIBUTE_FIELD);



    // ---------------------------------------------------------------------------------
    // -- IS CORE MATCHER
    // ---------------------------------------------------------------------------------

    /**
     * Matcher to check if passed element is a ExecutableElement.
     */
    public final static IsCoreMatcher<Element, ExecutableElement> IS_EXECUTABLE_ELEMENT = new IsCoreMatcher<>(new IsExecutableElementMatcher<Element>(), CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT);

    /**
     * Matcher to check if passed element is a TypeElement.
     */
    public final static IsCoreMatcher<Element, TypeElement> IS_TYPE_ELEMENT = new IsCoreMatcher<>(new IsTypeElementMatcher<Element>(), CoreMatcherValidationMessages.IS_TYPE_ELEMENT);

    /**
     * Matcher to check if passed element is a VariableElement.
     */
    public final static IsCoreMatcher<Element, VariableElement> IS_VARIABLE_ELEMENT = new IsCoreMatcher<>(new IsVariableElementMatcher<Element>(), CoreMatcherValidationMessages.IS_VARIABLE_ELEMENT);

    /**
     * Matcher to check if passed element is a PackageElement.
     */
    public final static IsCoreMatcher<Element, PackageElement> IS_PACKAGE_ELEMENT = new IsCoreMatcher<>(new IsPackageElementMatcher<Element>(), CoreMatcherValidationMessages.IS_PACKAGE_ELEMENT);

    /**
     * Matcher to check if passed element represents a package.
     */
    public final static IsElementBasedCoreMatcher<PackageElement> IS_PACKAGE = new IsElementBasedCoreMatcher<>(new IsPackageMatcher<Element>(), CoreMatcherValidationMessages.IS_PACKAGE);

    /**
     * Matcher to check if passed element represents a class.
     */
    public final static IsElementBasedCoreMatcher<TypeElement> IS_CLASS = new IsElementBasedCoreMatcher<>(new IsClassMatcher<Element>(), CoreMatcherValidationMessages.IS_CLASS);

    /**
     * Matcher to check if passed element represents an enum.
     */
    public final static IsElementBasedCoreMatcher<TypeElement> IS_ENUM = new IsElementBasedCoreMatcher<>(new IsEnumMatcher<Element>(), CoreMatcherValidationMessages.IS_ENUM);

    /**
     * Matcher to check if passed element represents an interface.
     */
    public final static IsElementBasedCoreMatcher<TypeElement> IS_INTERFACE = new IsElementBasedCoreMatcher<>(new IsInterfaceMatcher<Element>(), CoreMatcherValidationMessages.IS_INTERFACE);

    /**
     * Matcher to check if passed element represents a method.
     */
    public final static IsElementBasedCoreMatcher<ExecutableElement> IS_METHOD = new IsElementBasedCoreMatcher<>(new IsMethodMatcher<Element>(), CoreMatcherValidationMessages.IS_METHOD);

    /**
     * Matcher to check if passed element represents a constructor.
     */
    public final static IsElementBasedCoreMatcher<ExecutableElement> IS_CONSTRUCTOR = new IsElementBasedCoreMatcher<>(new IsConstructorMatcher<Element>(), CoreMatcherValidationMessages.IS_CONSTRUCTOR);

    /**
     * Matcher to check if passed element represents an annotation type.
     */
    public final static IsElementBasedCoreMatcher<TypeElement> IS_ANNOTATION_TYPE = new IsElementBasedCoreMatcher<>(new IsAnnotationTypeMatcher<Element>(), CoreMatcherValidationMessages.IS_ANNOTATION_TYPE);

    /**
     * Matcher to check if passed element represents a field.
     */
    public final static IsElementBasedCoreMatcher<VariableElement> IS_FIELD = new IsElementBasedCoreMatcher<>(new IsFieldMatcher<Element>(), CoreMatcherValidationMessages.IS_FIELD);

    /**
     * Matcher to check if passed element represents a parameter.
     */
    public final static IsElementBasedCoreMatcher<VariableElement> IS_PARAMETER = new IsElementBasedCoreMatcher<>(new IsParameterMatcher<Element>(), CoreMatcherValidationMessages.IS_PARAMETER);




}
