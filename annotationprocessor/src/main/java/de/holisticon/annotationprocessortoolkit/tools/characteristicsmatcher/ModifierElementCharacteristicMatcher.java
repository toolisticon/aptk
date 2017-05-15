package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Class for matching {@link Modifier}.
 */
public class ModifierElementCharacteristicMatcher implements GenericElementCharacteristicMatcher<Modifier> {

    @Override
    public boolean checkForMatchingCharacteristic(Element element, Modifier toCheckFor) {
        return (element != null && toCheckFor != null) && element.getModifiers().contains(toCheckFor);
    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(Modifier toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.name() : null;
    }

}
