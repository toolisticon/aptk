package io.toolisticon.aptk.tools.command;


import javax.lang.model.element.Element;

/**
 * Command that can be used with fluent validator and filter.
 */
public interface CommandWithReturnType <ELEMENT_TYPE extends Element, TARGET_TYPE> {

    TARGET_TYPE execute(ELEMENT_TYPE element);

}