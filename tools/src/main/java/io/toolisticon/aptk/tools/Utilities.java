package io.toolisticon.aptk.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class that offers some basic, commonly used utility methods.
 */
public final class Utilities {

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

        Set<T> set = new HashSet<>(array.length);

        for (T element : array) {

            // don't add null values to set
            if (element != null) {
                set.add(element);
            }

        }

        return set;

    }


    /**
     * Creates a Set that is containing the varargs arguments.
     *
     * @param varargs the elements to be added to the set
     * @param <T>     The arrays base type
     * @return a Set that contains all varargs arguments or an empty Set if no varargs arguments have been used
     */
    @SafeVarargs
    public static <T> Set<T> convertVarargsToSet(T... varargs) {

        return varargs != null ? new HashSet<>(Arrays.asList(varargs)) : new HashSet<>();

    }

    /**
     * Creates a List that is containing the varargs arguments.
     *
     * @param varargs the elements to be added to the list
     * @param <T>     the arrays base type
     * @return a List that contains all varargs arguments or an empty List if no varargs arguments have been used
     */
    @SafeVarargs
    public static <T> List<T> convertVarargsToList(T... varargs) {

        return varargs != null ? Arrays.asList(varargs) : new ArrayList<>();

    }

    /**
     * Creates an array that is containing the varargs arguments.
     *
     * @param varargs the elements to be added to the array
     * @param <T>     the arrays base type
     * @return an array that contains all varargs arguments or an array of length 0 if no varargs arguments have been used
     */
    @SafeVarargs
    public static <T> T[] convertVarargsToArray(T... varargs) {
        final Object[] e = {};

        if (varargs == null) {
            return (T[]) e;
        }
        return varargs;

    }

}
