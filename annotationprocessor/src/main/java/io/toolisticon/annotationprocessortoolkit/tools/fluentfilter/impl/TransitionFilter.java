package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import javax.lang.model.element.Element;
import java.util.List;

/**
 *
 */
public interface TransitionFilter<SOURCE_ELEMENT extends Element, TARGET_ELEMENT extends Element> {

    /**
     * Does a transition.
     *
     * @param elements the Elements to apply the transition to
     * @return a new list containing the transitions result Elements
     */
    List<TARGET_ELEMENT> transition(List<SOURCE_ELEMENT> elements);

}
