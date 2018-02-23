package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;


/**
 * Matcher to check Parameters of ExecutableElement.
 */
public class ByParameterTypeFqnMatcher implements CriteriaMatcher<ExecutableElement, String[]> {

    public ByParameterTypeFqnMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(ExecutableElement element, String[] toCheckFor) {

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

            TypeMirror parameterTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor[i]);
            if (parameterTypeMirror == null) {
                return false;
            }

            if (!executableElement.getParameters().get(i).asType().equals(parameterTypeMirror)) {
                return false;
            }
        }


        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String[] toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? "" : null;
    }

}
