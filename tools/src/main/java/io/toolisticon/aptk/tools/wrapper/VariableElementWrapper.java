package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.VariableElement;

/**
 * Wrapper for variable Element
 */
public class VariableElementWrapper extends ElementWrapper<VariableElement>{


    private VariableElementWrapper(VariableElement variableElement) {
        super(variableElement);
    }

    public Object getConstantValue() {
        return this.element.getConstantValue();
    }

    public static VariableElementWrapper wrap(VariableElement variableElement) {
        return new VariableElementWrapper(variableElement);
    }

}
