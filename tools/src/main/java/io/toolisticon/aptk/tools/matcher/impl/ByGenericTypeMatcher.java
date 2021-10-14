package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.generics.GenericType;
import io.toolisticon.aptk.tools.generics.GenericTypeParameter;
import io.toolisticon.aptk.tools.generics.GenericTypeWildcard;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;

/**
 * A matcher for generic types.
 */
public class ByGenericTypeMatcher implements CriteriaMatcher<Element, GenericType> {

    public ByGenericTypeMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, GenericType toCheckFor) {

        return (element != null && toCheckFor != null) && TypeUtils.Generics.genericTypeEquals(element.asType(), toCheckFor);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(GenericType toGetStringRepresentationFor) {

        if (toGetStringRepresentationFor == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        createStringRepresentationRecursively(stringBuilder, toGetStringRepresentationFor);

        return stringBuilder.toString();

    }

    private void createStringRepresentationRecursively(StringBuilder stringBuilder, GenericType toGetStringRepresentationFor) {


        stringBuilder.append(TypeUtils.getTypes().erasure(toGetStringRepresentationFor.getRawType()).toString());


        // Check type parameters
        if (toGetStringRepresentationFor.getTypeParameters().length > 0) {

            stringBuilder.append("<");


            boolean first = true;
            // Now check type parameters recursively
            for (int i = 0; i < toGetStringRepresentationFor.getTypeParameters().length; i++) {

                if (first) {
                    first = false;
                } else {
                    stringBuilder.append(", ");
                }

                GenericTypeParameter currentGenericTypeParameter = toGetStringRepresentationFor.getTypeParameters()[i];

                switch (currentGenericTypeParameter.getType()) {

                    case WILDCARD:

                        GenericTypeWildcard wildcard = (GenericTypeWildcard) currentGenericTypeParameter;

                        if (wildcard.isPureWildcard()) {

                            stringBuilder.append("?");

                        } else if (wildcard.hasExtendsBound()) {

                            stringBuilder.append("? extends ");

                            createStringRepresentationRecursively(stringBuilder, wildcard.getExtendsBound());


                        } else if (wildcard.hasSuperBound()) {

                            stringBuilder.append("? super ");

                            createStringRepresentationRecursively(stringBuilder, wildcard.getSuperBound());

                        }

                        break;


                    case GENERIC_TYPE:
                        createStringRepresentationRecursively(stringBuilder, (GenericType) currentGenericTypeParameter);
                        break;

                    default:
                        // does nothing

                }


            }
            stringBuilder.append(">");
        }


    }


}
