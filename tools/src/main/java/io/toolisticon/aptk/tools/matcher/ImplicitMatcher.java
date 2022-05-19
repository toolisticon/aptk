package io.toolisticon.aptk.tools.matcher;

import javax.lang.model.element.Element;

/**
 * Matcher for an implicit criteria.
 *
 * @param <E> The Element type of the matcher
 */
public interface ImplicitMatcher<E extends Element> extends BaseMatcher<E> {

    /**
     * Checks if element matches implicit criteria.
     * @param element the element to check
     * @return true, if element matches implicit criteria, otherwise false.
     */
    boolean check(E element);


}
