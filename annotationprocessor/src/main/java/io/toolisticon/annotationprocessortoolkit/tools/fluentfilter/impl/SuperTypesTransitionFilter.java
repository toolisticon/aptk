package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Transition to super types of passed elements.
 */
public class SuperTypesTransitionFilter implements TransitionFilter<TypeElement,TypeElement> {

    @Override
    public List<TypeElement> transition(List<TypeElement> elements) {

        List<TypeElement> superTypeElements = new ArrayList<>();

        if (elements != null) {

            for (TypeElement element : elements) {

                superTypeElements.addAll(Arrays.asList(ElementUtils.AccessTypeHierarchy.getSuperTypeElements(element)));

            }

        }

        return superTypeElements;
    }

}

