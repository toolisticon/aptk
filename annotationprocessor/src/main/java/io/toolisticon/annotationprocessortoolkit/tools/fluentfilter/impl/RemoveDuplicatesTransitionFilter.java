package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Removes duplicate elements.
 */
public class RemoveDuplicatesTransitionFilter implements ElementBasedTransitionFilter<Element> {

    @Override
    public List<Element> transition(List<? extends Element> elements) {

        List<Element> filteredElements = new ArrayList<Element>();

        if (elements != null) {

            Set<Element> elementLookUpTable = new HashSet<Element>();

            for (Element element : elements) {

                if (!elementLookUpTable.contains(element)) {

                    elementLookUpTable.add(element);
                    filteredElements.add(element);

                }

            }

        }

        return filteredElements;
    }

}

