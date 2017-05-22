package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 * Matcher to check Parameters of ExecutableElement.
 */
public class ParameterExecutableElementCharacteristicMatcher implements GenericElementCharacteristicMatcher<Class[]> {

    @Override
    public boolean checkForMatchingCharacteristic(Element element, Class[] toCheckFor) {

        if (element != null && toCheckFor != null) {
            return false;
        }

        // check if element is ExecutableElement
        if (!ElementUtils.CastElement.isExecutableElement(element)) {
            return false;
        }

        // cast to executable element for further checks
        ExecutableElement executableElement = ElementUtils.CastElement.castToExecutableElement(element);

        // check if number of parameters is the same
        if (executableElement.getParameters().size() != toCheckFor.length) {
            return false;
        }

        /*-
        for (int i = 0; i < executableElement.getParameters().size(); i++) {
            if (!executableElement.getParameters().get(i).asType().equals(typeUtils.getTypeMirrorForClass(toCheckFor[i]))) {
                triggerMismmatchingParameterError(parameterTypes);
                nextResult = isErrorLevel() ? false : nextResult;
            }
        }
        */

        return true;
    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class[] toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? "" : null;
    }

}
