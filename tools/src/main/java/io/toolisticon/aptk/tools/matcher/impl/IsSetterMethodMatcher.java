package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

public class IsSetterMethodMatcher implements ImplicitMatcher<ExecutableElement> {

    @Override
    public boolean check(ExecutableElement element) {

        if (element == null) {
            return false;
        }

        return FluentElementValidator.createFluentElementValidator(element)
                .applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC)
                .applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.STATIC, Modifier.ABSTRACT)
                .applyValidator(CoreMatchers.BY_NUMBER_OF_PARAMETERS).hasOneOf(1)
                .applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                .applyValidator(CoreMatchers.BY_REGEX_NAME).hasOneOf("set.*")
                .justValidate();

    }
}
