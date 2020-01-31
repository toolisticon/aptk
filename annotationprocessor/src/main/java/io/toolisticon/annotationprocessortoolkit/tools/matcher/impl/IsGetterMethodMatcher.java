package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class IsGetterMethodMatcher implements ImplicitMatcher<ExecutableElement> {

    @Override
    public boolean check(ExecutableElement element) {

        if (element == null) {
            return false;
        }

        if (!FluentElementValidator.createFluentElementValidator(element)
                .applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC)
                .applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.STATIC, Modifier.ABSTRACT)
                .applyValidator(CoreMatchers.HAS_NO_PARAMETERS)
                .applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                .justValidate()
        ) {
            return false;
        }

        TypeMirror returnType = element.getReturnType();


        String methodName = element.getSimpleName().toString();

        return methodName.startsWith("get") ||
                (TypeUtils.TypeComparison.isTypeEqual(returnType, TypeUtils.TypeRetrieval.getTypeMirror(boolean.class))
                        && ((methodName.startsWith("is") || methodName.startsWith("has"))));

    }
}
