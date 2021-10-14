package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

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


        // check if number of parameters is the same
        if (element.getParameters().size() != toCheckFor.length) {
            return false;
        }


        for (int i = 0; i < element.getParameters().size(); i++) {

            TypeMirror parameterTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor[i]);
            if (parameterTypeMirror == null) {
                return false;
            }

            if (!element.getParameters().get(i).asType().equals(parameterTypeMirror)) {
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

        if (toGetStringRepresentationFor != null) {
            StringBuilder stringBuilder = new StringBuilder("[");
            boolean isFirst = true;
            for (String element : toGetStringRepresentationFor) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(element);
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        } else {
            return null;
        }

    }

}
