package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * Class for checking existence of one annotation on an element.
 */
public class ByAnnotationMatcher implements CriteriaMatcher<Element, Class<? extends Annotation>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class<? extends Annotation> toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.getCanonicalName() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, Class<? extends Annotation> toCheckFor) {
        return element != null && toCheckFor != null && element.getAnnotation(toCheckFor) != null;
    }

}
