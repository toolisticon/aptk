package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Transition to child elements of passed elements.
 */
public class ChildElementsTransitionFilter implements ElementBasedTransitionFilter<Element> {

    @Override
    public  List<Element> transition(List<? extends Element> elements) {

        List<Element> childElements = new ArrayList<>();

        if (elements != null) {

            for (Element element : elements) {

                childElements.addAll(element.getEnclosedElements());

            }

        }

        return childElements;
    }

}
