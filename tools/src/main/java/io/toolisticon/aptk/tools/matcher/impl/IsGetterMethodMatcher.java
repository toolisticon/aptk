package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

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
                .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC)
                .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.STATIC, Modifier.ABSTRACT)
                .applyValidator(AptkCoreMatchers.HAS_NO_PARAMETERS)
                .applyInvertedValidator(AptkCoreMatchers.HAS_VOID_RETURN_TYPE)
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
