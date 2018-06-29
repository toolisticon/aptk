package io.toolisticon.annotationprocessortoolkit.templating;

import io.toolisticon.annotationprocessortoolkit.templating.exceptions.InvalidPathException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to access Model.
 * Model can either be set via TheadLocal or passed in via method parameter.
 */
public class ModelPathResolver {

    /**
     *
     */
    public static ThreadLocal<Map<String, Object>> modelMapThreadLocal = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    /**
     * Static class that holds the result of the resolved path.
     */
    public static class ResolvedModelPathResult {

        private final Object value;
        private final Class type;

        public ResolvedModelPathResult(Class type, Object value) {
            this.value = value;
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public Class getType() {
            return type;
        }
    }

    /**
     * Resolves path on the model.
     * This method can be used to get model from ThreadLocal.
     *
     * @param path the path to resolve
     * @return
     */
    public static ResolvedModelPathResult resolveModelPath(String path) {
        return resolveModelPath(modelMapThreadLocal.get(), path);
    }


    /**
     * Resolves path on the model.
     *
     * @param model the model
     * @param path  the path to resolve
     * @return
     */
    public static ResolvedModelPathResult resolveModelPath(Map<String, Object> model, String path) {

        if (model == null || path == null) {
            return null;
        }

        // break path strings to path tokens
        String[] pathTokens = path.split("[.]");

        Object currentNode = model;
        Class currentNodeType = null;

        for (String currentPathToken : pathTokens) {

            if (currentNode == null) {
                throw new InvalidPathException("Path cannot be resolved. Encountered null value in path '" + path + "'  so token '" + currentPathToken + "' cannot be applied.");
            } else if (currentNode.getClass().isArray()) {
                throw new InvalidPathException("Path cannot be resolved. Encountered array in path '" + path + "' so token '" + currentPathToken + "' cannot be applied.");
            } else if (currentNode instanceof Map) {

                if (!(((Map) currentNode).containsKey(currentPathToken))) {
                    throw new InvalidPathException("Path cannot be resolved. Encountered Map in path '" + path + "' which has no key " + currentPathToken + ".");
                }

                currentNode = ((Map) currentNode).get(currentPathToken);

                // Now use values type
                if (currentNode != null) {
                    currentNodeType = currentNode.getClass();
                }

            } else {

                // POJOS

                // get getter
                String getterName = getGetter(currentNode, currentPathToken);

                if (getterName == null) {
                    throw new InvalidPathException("Path '" + path + "' cannot be resolved. Path token " + currentPathToken + " not resolvable");
                }

                // now call method via reflection
                try {
                    Method getterMethodToCall = currentNode.getClass().getMethod(getterName);

                    currentNode = getterMethodToCall.invoke(currentNode);
                    currentNodeType = getterMethodToCall.getReturnType();

                } catch (NoSuchMethodException e) {
                    // can be ignored - because it's guaranteed that the method exists
                } catch (Exception e) {
                    throw new InvalidPathException("Path '\" + path + \"' cannot be resolved. Cannot invoke getter method of token '" + currentPathToken + "'", e);
                }


            }

        }

        return new ResolvedModelPathResult(currentNodeType, currentNode);

    }

    /**
     * Gets the name of the getter.
     * Getter method must take no parameters and must be public
     *
     * @param instance          the instance to search the getter in
     * @param fieldNameOrGetter the name of the field or method to get the getter for
     * @return the name of the method or getter method or null if no callable getter can be found
     */
    protected static String getGetter(Object instance, String fieldNameOrGetter) {

        final String[] GETTER_PREFIXES = {"get", "is", "has"};

        final String trimmedFieldNameOrGetter = fieldNameOrGetter != null ? fieldNameOrGetter.trim() : null;

        if (instance == null || trimmedFieldNameOrGetter == null || trimmedFieldNameOrGetter.length() == 0) {
            return null;
        }

        try {

            // check if passed fieldNameOrGetter is already method without parameter
            Method method = instance.getClass().getMethod(trimmedFieldNameOrGetter.trim());

            if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                return trimmedFieldNameOrGetter;
            }


        } catch (NoSuchMethodException e) {
            // ignore this
        }

        // now check existence of method with getter prefixes - return first match
        for (String getterPrefix : GETTER_PREFIXES) {

            // construct getter name
            String getterName = getterPrefix + trimmedFieldNameOrGetter.substring(0, 1).toUpperCase() + trimmedFieldNameOrGetter.substring(1);


            try {

                // check if passed fieldNameOrGetter is already method without parameter
                Method method = instance.getClass().getMethod(getterName);
                if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                    return getterName;
                }

            } catch (NoSuchMethodException e) {
                // ignore this
            }


        }

        return null;

    }

}
