package io.toolisticon.annotationprocessortoolkit.tools.matcher;

import javax.lang.model.element.Element;

/**
 * Matcher for an implicit criteria.
 */
public interface ImplicitMatcher<E extends Element> extends BaseMatcher<E> {

    /**
     * Checks if element matches implicit criteria.
     *
     * @return true, if element matches implicit criteria, otherwise false.
     */
    boolean check(E element);


}
