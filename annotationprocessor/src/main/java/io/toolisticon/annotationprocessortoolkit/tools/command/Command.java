package io.toolisticon.annotationprocessortoolkit.tools.command;

import javax.lang.model.element.Element;

/**
 * Command that can be used with fluent validator and filter.
 */
public interface Command <ELEMENT_TYPE extends Element> {

    void execute(ELEMENT_TYPE element);

}
