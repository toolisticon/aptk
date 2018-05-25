package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter;

import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.ChildElementsTransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.ElementBasedTransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.ParentElementTransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.RemoveDuplicatesTransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.SuperTypesTransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.TransitionFilter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Convenience class to access all given {@link TransitionFilter}.
 */
public final class TransitionFilters {

    /**
     * Hidden constructor.
     */
    private TransitionFilters() {

    }

    /**
     * Transition to all direct childElements.
     */
    public final static ElementBasedTransitionFilter<Element> CHILD_ELEMENTS = new ChildElementsTransitionFilter();
    /**
     * Transition to all direct parentElements.
     */
    public final static ElementBasedTransitionFilter<Element> PARENT_ELEMENTS = new ParentElementTransitionFilter();
    /**
     * Removes all duplicate elements.
     * This is not really a transition.
     * But it differs from CoreFilters that Elements will be compared with each other.
     */
    public final static ElementBasedTransitionFilter<Element> REMOVE_DUPLICATES_ELEMENTS = new RemoveDuplicatesTransitionFilter();
    /**
     * Transition to all super types (not just the direct parents).
     */
    public final static TransitionFilter<TypeElement, TypeElement> SUPER_TYPE_ELEMENTS = new SuperTypesTransitionFilter();


}
