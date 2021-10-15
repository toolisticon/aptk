package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

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

        // check if number of parameters is the same
        if (element.getParameters().size() != toCheckFor.length) {
            return false;
        }


        for (int i = 0; i < element.getParameters().size(); i++) {

            // must handle different cases
            VariableElement parameterElement = element.getParameters().get(i);

            if(!TypeUtils.TypeComparison.isErasedTypeEqual(parameterElement.asType(), TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor[i]) )){
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
            for (Class<?> element : toGetStringRepresentationFor) {
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
