package io.toolisticon.annotationprocessortoolkit.tools.command.impl;

import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils;
import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils.AttributeResult;
import io.toolisticon.annotationprocessortoolkit.tools.command.CommandWithReturnType;

import javax.lang.model.element.TypeElement;

/**
 * Get all attributes of  passed TypeElement and it's parents.
 * attribute = field with adequate getter and setter method
 */
public class GetAttributesCommandWithInheritance implements CommandWithReturnType<TypeElement, AttributeResult[]> {

    public final static GetAttributesCommandWithInheritance INSTANCE = new GetAttributesCommandWithInheritance();

    @Override
    public AttributeResult[] execute(TypeElement element) {

        return BeanUtils.getAttributesWithInheritance(element);
    }
}
