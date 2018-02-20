package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * Class for checking existence of one annotation on an element.
 */
public class ByAnnotationMatcher implements CharacteristicsMatcher<Element, Class<? extends Annotation>> {

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
        return element == null || toCheckFor == null ? false : element.getAnnotation(toCheckFor) != null;
    }

}
