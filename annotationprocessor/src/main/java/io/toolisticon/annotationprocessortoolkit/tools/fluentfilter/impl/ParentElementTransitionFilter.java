package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Transition to parent elements of passed elements.
 */
public class ParentElementTransitionFilter implements ElementBasedTransitionFilter<Element> {

    @Override
    public List<Element> transition(List<? extends Element> elements) {

        List<Element> parentElements = new ArrayList<Element>();

        if (elements != null) {

            for (Element element : elements) {

                parentElements.add(element.getEnclosingElement());

            }

        }

        return parentElements;
    }

}
