package de.holisticon.annotationprocessortoolkit.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class that offers some basic utility methods.
 */
public class Utilities {

    /**
     * Hidden constructor.
     */
    private Utilities() {

    }

    /**
     * Utility function that converts an array into a set.
     * Doubled elements as well as null values will be removed.
     *
     * @param array the array to convert
     * @param <T>   the generic type of the array
     * @return the set containing all elements of passed array, doubled elements will just appear once in returned Set.
     */
    public static <T> Set<T> convertArrayToSet(T[] array) {

        if (array == null) {
            return null;
        }

        Set<T> set = new HashSet<T>(array.length);

        for (T element : array) {

            // don't add null values to set
            if (element != null) {
                set.add(element);
            }

        }

        return set;

    }


    public static <T> Set<T> convertVarargsToSet(T... varargs) {

        return varargs != null ? new HashSet<T>(Arrays.asList(varargs)) : new HashSet<T>();

    }

    public static <T> List<T> convertVarargsToList(T... varargs) {

        return varargs != null ? Arrays.asList(varargs) : new ArrayList<T>();

    }

    public static <T> T[] convertVarargsToArray(T... varargs) {

        return varargs;

    }

}
