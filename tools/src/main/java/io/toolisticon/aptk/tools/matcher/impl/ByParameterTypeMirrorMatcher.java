package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tobiasstamann on 09.05.18.
 */
public class ByParameterTypeMirrorMatcher implements CriteriaMatcher<ExecutableElement, TypeMirror[]> {

    public ByParameterTypeMirrorMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(ExecutableElement element, TypeMirror[] toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }


        // check if number of parameters is the same
        if (element.getParameters().size() != toCheckFor.length) {
            return false;
        }


        for (int i = 0; i < element.getParameters().size(); i++) {

            TypeMirror parameterTypeMirror = toCheckFor[i];
            if (parameterTypeMirror == null) {
                return false;
            }

            if(!TypeUtils.TypeComparison.isErasedTypeEqual(element.getParameters().get(i).asType(), parameterTypeMirror )){
                return false;
            }
        }


        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(TypeMirror[] toGetStringRepresentationFor) {

        if (toGetStringRepresentationFor != null) {
            StringBuilder stringBuilder = new StringBuilder("[");
            boolean isFirst = true;
            for (TypeMirror element : toGetStringRepresentationFor) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(element.toString());
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        } else {
            return null;
        }

    }

}

