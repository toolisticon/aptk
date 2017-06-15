package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;


/**
 * Matcher to check Parameters of ExecutableElement.
 */
public class ParameterFQNExecutableElementCharacteristicMatcher extends GenericElementCharacteristicMatcherWithToolsSupport<String[]> {

    public ParameterFQNExecutableElementCharacteristicMatcher(FrameworkToolWrapper tools) {
        super(tools);
    }


    @Override
    public boolean checkForMatchingCharacteristic(Element element, String[] toCheckFor) {

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

            TypeMirror parameterTypeMirror = TypeUtils.getTypeUtils(tools).getTypeMirror(toCheckFor[i]);
            if (parameterTypeMirror == null) {
                return false;
            }

            if (!executableElement.getParameters().get(i).asType().equals(parameterTypeMirror)) {
                return false;
            }
        }


        return true;
    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(String[] toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? "" : null;
    }

}
