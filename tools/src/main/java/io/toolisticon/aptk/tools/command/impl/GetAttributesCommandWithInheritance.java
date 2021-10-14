package io.toolisticon.aptk.tools.command.impl;

import io.toolisticon.aptk.tools.BeanUtils;
import io.toolisticon.aptk.tools.BeanUtils.AttributeResult;
import io.toolisticon.aptk.tools.command.CommandWithReturnType;

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
