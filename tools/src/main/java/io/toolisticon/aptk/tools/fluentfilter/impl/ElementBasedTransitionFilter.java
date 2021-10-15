package io.toolisticon.aptk.tools.fluentfilter.impl;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * Element based transition filter.
 */
public interface ElementBasedTransitionFilter<TARGET_ELEMENT extends Element> {

    /**
     * Does a transition.
     *
     * @param elements the Elements to apply the transition to
     * @return a new list containing the transitions result Elements
     */
    List<TARGET_ELEMENT> transition(List<? extends Element> elements);

}