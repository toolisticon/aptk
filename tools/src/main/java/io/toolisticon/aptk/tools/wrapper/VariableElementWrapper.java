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

    /**
     * Wraps a VariableElement.
     * Will throw IllegalArgumentException if passed element is null.
     * @param variableElement the element to wrap
     * @return a wrapper instance
     */
    public static VariableElementWrapper wrap(VariableElement variableElement) {
        return new VariableElementWrapper(variableElement);
    }

}
