package de.holisticon.annotationprocessortoolkit.internal;

import java.util.HashSet;
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
     * Utilit function that converts an array into a set.
     * Doubled elements will be removed.
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
            set.add(element);
        }

        return set;

    }

}
