package io.toolisticon.annotationprocessortoolkit.validators;

/**
 * Utility functions used by validators.
 */
public class ValidatorUtilities {


    public static <T> String createStringRepresentationOfArray(T[] elements) {


        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (elements != null) {

            boolean isFirst = true;
            for (T type : elements) {

                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(", ");
                }

                sb.append(type.toString());

            }
        }

        sb.append("]");

        return sb.toString();
    }
}
