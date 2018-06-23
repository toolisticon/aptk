package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;

/**
 * Matcher to check if field is an attribute.
 * Field must not be static.
 * Getter must not be static nor abstract, must return fields type and take no parameters
 * Setter must not be static nor abstract, must have void return type and one parameter of fields type
 */
public class IsAttributeFieldMatcher implements ImplicitMatcher<VariableElement> {

    @Override
    public boolean check(VariableElement element) {

        if (element == null || element.getKind() != ElementKind.FIELD) {
            return false;
        }


        return BeanUtils.isAttribute(element);
    }
}
