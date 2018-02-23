package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;

/**
 * Matcher to check Parameters of ExecutableElement.
 */
public class ByParameterTypeMatcher implements CriteriaMatcher<ExecutableElement, Class[]> {

    public ByParameterTypeMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(ExecutableElement element, Class[] toCheckFor) {

        if (element == null || toCheckFor == null) {
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


        for (int i = 0; i < executableElement.getParameters().size(); i++) {
            if (!executableElement.getParameters().get(i).asType().equals(
                    TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor[i]))
                    ) {
                return false;
            }
        }


        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class[] toGetStringRepresentationFor) {
        if (toGetStringRepresentationFor != null) {
            StringBuilder stringBuilder = new StringBuilder("[");
            boolean isFirst = true;
            for (Class element : toGetStringRepresentationFor) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(element.getCanonicalName());
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

}
