package io.toolisticon.aptk.tools.command.impl;

import io.toolisticon.aptk.tools.command.CommandWithReturnType;
import io.toolisticon.aptk.tools.BeanUtils;
import io.toolisticon.aptk.tools.BeanUtils.AttributeResult;

import javax.lang.model.element.TypeElement;

/**
 * Get all attributes of  passed TypeElement.
 * attribute = field with adequate getter and setter method
 */
public class GetAttributesCommand implements CommandWithReturnType<TypeElement, AttributeResult[]> {

    public final static GetAttributesCommand INSTANCE = new GetAttributesCommand();

    @Override
    public AttributeResult[] execute(TypeElement element) {

        return BeanUtils.getAttributes(element);

    }


}
