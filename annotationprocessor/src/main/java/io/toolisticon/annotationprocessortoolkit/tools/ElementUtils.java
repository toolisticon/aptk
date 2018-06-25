package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Utility class which helps to handle different {@link Element} related tasks.
 */
public final class ElementUtils {

    /**
     * Hidden constructor that prevents instantiation.
     */
    private ElementUtils() {

    }

    /**
     * Convenience utility class for checking the {@link ElementKind} of an Element.
     */
    public static final class CheckKindOfElement {

        /**
         * Hidden constructor.
         */
        private CheckKindOfElement() {
            // does nothing except preventing instantiation
        }


        /**
         * Checks if passed Element instance is of kind enum.
         *
         * @param e the element to check
         * @return true if passed element is of kind enum, otherwise false
         */
        public static boolean isEnum(Element e) {
            return isOfKind(e, ElementKind.ENUM);
        }

        /**
         * Checks if passed Element instance is of kind class.
         *
         * @param e the element to check
         * @return true if passed element is of kind class, otherwise false
         */
        public static boolean isClass(Element e) {
            return isOfKind(e, ElementKind.CLASS);
        }

        /**
         * Checks if passed Element instance is of kind interface.
         *
         * @param e the element to check
         * @return true if passed element is of kind enum, otherwise false
         */
        public static boolean isInterface(Element e) {
            return isOfKind(e, ElementKind.INTERFACE);
        }

        /**
         * Checks if passed Element instance is of kind method.
         *
         * @param e the element to check
         * @return true if passed element is of kind method, otherwise false
         */
        public static boolean isMethod(Element e) {
            return isOfKind(e, ElementKind.METHOD);
        }


        /**
         * Checks if passed Element instance is of kind parameter.
         *
         * @param e the element to check
         * @return true if passed element is of kind parameter, otherwise false
         */
        public static boolean isParameter(Element e) {
            return isOfKind(e, ElementKind.PARAMETER);
        }

        /**
         * Checks if passed Element instance is of kind constructor.
         *
         * @param e the element to check
         * @return true if passed element is of kind constructor, otherwise false
         */
        public static boolean isConstructor(Element e) {
            return isOfKind(e, ElementKind.CONSTRUCTOR);
        }

        /**
         * Checks if passed Element instance is of kind field.
         *
         * @param e the element to check
         * @return true if passed element is of kind field, otherwise false
         */
        public static boolean isField(Element e) {
            return isOfKind(e, ElementKind.FIELD);
        }

        /**
         * Checks if passed Element instance is of kind package.
         *
         * @param e the element to check
         * @return true if passed element is of kind package, otherwise false
         */
        public static boolean isPackage(Element e) {
            return isOfKind(e, ElementKind.PACKAGE);
        }

        /**
         * Checks if passed Element instance is of kind annotation.
         *
         * @param e the element to check
         * @return true if passed element is of kind annotation, otherwise false
         */
        public static boolean isAnnotation(Element e) {
            return isOfKind(e, ElementKind.ANNOTATION_TYPE);
        }

        /**
         * Checks if passed Element instance is of a specific kind.
         *
         * @param e    the element to check
         * @param kind the kind to check for
         * @return true if passed element is of the passed kind, otherwise false
         */
        public static boolean isOfKind(Element e, ElementKind kind) {
            return (kind != null && e != null) ? kind.equals(e.getKind()) : false;
        }

    }

    /**
     * Convenience utility class for checking if the kind of an {@link Element}.
     */
    public static final class CastElement {

        // look up tables for the different kind of types
        private static final Set<ElementKind> TYPE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.CLASS, ElementKind.INTERFACE, ElementKind.ENUM);
        private static final Set<ElementKind> VARIABLE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.PARAMETER, ElementKind.FIELD);
        private static final Set<ElementKind> EXECUTABLE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.CONSTRUCTOR, ElementKind.METHOD, ElementKind.ANNOTATION_TYPE);
        private static final Set<ElementKind> PACKAGE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.PACKAGE);


        /**
         * Hidden constructor.
         */
        private CastElement() {
            // does nothing except preventing instantiation
        }


        /**
         * Checks if passed element can be casted to TypeElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to TypeElement, otherwise false
         */
        public static boolean isTypeElement(Element e) {
            return e != null && TYPE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to VariableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to VariableElement, otherwise false
         */
        public static boolean isVariableElement(Element e) {
            return e != null && VARIABLE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to ExecutableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to ExecutableElement, otherwise false
         */
        public static boolean isExecutableElement(Element e) {
            return e != null && EXECUTABLE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to PackageElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to PackageElement, otherwise false
         */
        public static boolean isPackageElement(Element e) {
            return e != null && PACKAGE_ELEMENT_KIND_LUT.contains(e.getKind());
        }


        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castClass(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castInterface(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castEnum(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static VariableElement castParameter(Element e) {
            return castToVariableElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static VariableElement castField(Element e) {
            return castToVariableElement(e);
        }

        /**
         * Casts an element to TypeElement.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast to TypeElement
         */
        public static TypeElement castToTypeElement(Element e) {
            return (TypeElement) e;
        }

        /**
         * Casts an element to VariableElement.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast to TypeElement
         */
        public static VariableElement castToVariableElement(Element e) {
            return (VariableElement) e;
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static ExecutableElement castConstructor(Element e) {
            return castToExecutableElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static ExecutableElement castMethod(Element e) {
            return castToExecutableElement(e);
        }

        /**
         * Casts an element to ExecutableElement.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast to ExecutableElement
         */
        public static ExecutableElement castToExecutableElement(Element e) {
            return (ExecutableElement) e;
        }

        /**
         * Casts a list of elements to a list of elements.
         *
         * @param elementList  the list to be processed
         * @param typeToCastTo the Element sub class to cast to
         * @param <T>
         * @return a new list containing all elements of passed elementList
         */
        public static <T extends Element> List<T> castElementList(List<? extends Element> elementList, Class<T> typeToCastTo) {
            List<T> result = new ArrayList<T>(elementList.size());
            for (Element enclosedElement : elementList) {
                result.add((T) enclosedElement);
            }
            return result;
        }
    }

    /**
     * Convenience utility class for checking modifiers of an Element.
     */
    public static final class CheckModifierOfElement {

        /**
         * Hidden constructor.
         */
        private CheckModifierOfElement() {
            // does nothing except preventing instantiation
        }


        /**
         * Check if passed Element has public Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the public modifier, otherwise false
         */
        public static boolean hasPublicModifier(Element e) {
            return hasModifier(e, Modifier.PUBLIC);
        }

        /**
         * Check if passed Element has protected Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the protected modifier, otherwise false
         */
        public static boolean hasProtectedModifier(Element e) {
            return hasModifier(e, Modifier.PROTECTED);
        }

        /**
         * Check if passed Element has private Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the private modifier, otherwise false
         */
        public static boolean hasPrivateModifier(Element e) {
            return hasModifier(e, Modifier.PRIVATE);
        }

        /**
         * Check if passed Element has abstract Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the abstract modifier, otherwise false
         */
        public static boolean hasAbstractModifier(Element e) {
            return hasModifier(e, Modifier.ABSTRACT);
        }

    /*
     * Check if passed Element has default Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the default modifier, otherwise false
     */
        // Disabled until we move to Java 8
    /*
    public boolean hasDefaultModifier(Element e) {
        return hasModifier(e, Modifier.DEFAULT);
    }
    */

        /**
         * Check if passed Element has static Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the static modifier, otherwise false
         */
        public static boolean hasStaticModifier(Element e) {
            return hasModifier(e, Modifier.STATIC);
        }

        /**
         * Check if passed Element has final Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the final modifier, otherwise false
         */
        public static boolean hasFinalModifier(Element e) {
            return hasModifier(e, Modifier.FINAL);
        }

        /**
         * Check if passed Element has transient Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the transient modifier, otherwise false
         */
        public static boolean hasTransientModifier(Element e) {
            return hasModifier(e, Modifier.TRANSIENT);
        }

        /**
         * Check if passed Element has volatile Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the volatile modifier, otherwise false
         */
        public static boolean hasVolatileModifier(Element e) {
            return hasModifier(e, Modifier.VOLATILE);
        }

        /**
         * Check if passed Element has synchronized Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the synchronized modifier, otherwise false
         */
        public static boolean hasSynchronizedModifier(Element e) {
            return hasModifier(e, Modifier.SYNCHRONIZED);
        }

        /**
         * Check if passed Element has native Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the native modifier, otherwise false
         */
        public static boolean hasNativeModifier(Element e) {
            return hasModifier(e, Modifier.NATIVE);
        }

        /**
         * Check if passed Element has strictfp Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the strictfp modifier, otherwise false
         */
        public static boolean hasStrictfpModifier(Element e) {
            return hasModifier(e, Modifier.STRICTFP);
        }

        /**
         * Internal function that does handle the check for passed {@link Modifier}.
         *
         * @param e        the element to check
         * @param modifier the modifier to check for
         * @return true if passed element has modifier, otherwise false
         */
        private static boolean hasModifier(Element e, Modifier modifier) {
            return CoreMatchers.BY_MODIFIER.getValidator().hasAllOf(e, modifier);
        }

        /**
         * Gets the visibility modifier of an element.
         * @param element the element to check
         * @return the visibility modifierof an element or null for package privates
         */
        public static Modifier getVisibilityModifier(Element element){

            final Modifier[] modifiers = new Modifier[]{Modifier.PUBLIC,Modifier.PROTECTED, Modifier.PRIVATE};

            for (Modifier modifier : modifiers) {

                if (hasModifier(element, modifier)) {
                    return modifier;
                }


            }
            return null;
        }

    }

    /**
     * Utility cass to access enclosing elements of a {@link Element}.
     */
    public static final class AccessEnclosingElements {

        /**
         * Hidden constructor.
         */
        private AccessEnclosingElements() {
            // does nothing except preventing instantiation
        }

        /**
         * Gets a list of all enclosing parent elements of the passed element.
         *
         * @param element        the element to be processed
         * @param addRootElement defines if the passed element should be in the result list
         * @return a list of all enclosing elements of the passed element
         */
        public static List<Element> getFlattenedEnclosingElementsTree(Element element, boolean addRootElement) {
            return getFlattenedEnclosingElementsTree(element, addRootElement, Integer.MAX_VALUE);
        }

        /**
         * Gets a list of all enclosing parent elements up to a specific depth of the passed element.
         *
         * @param element        the element to be processed
         * @param addRootElement defines if the passed element should be in the result list
         * @param maxDepth       the maximal
         * @return a list of all enclosing elements of the passed element
         */
        public static List<Element> getFlattenedEnclosingElementsTree(Element element, boolean addRootElement, int maxDepth) {

            List<Element> result = new ArrayList<Element>();

            if (element == null) {
                return result;
            }

            if (addRootElement) {
                result.add(element);
            }

            int currentDepth = 0;
            Element currentParentElement = element.getEnclosingElement();

            while (currentParentElement != null && currentDepth < maxDepth) {

                result.add(currentParentElement);

                // prepare next iteration
                currentDepth++;
                currentParentElement = currentParentElement.getEnclosingElement();

            }

            return result;
        }

        /**
         * Gets the fist enclosing element parent of a specific {@link ElementKind}.
         * Allows f.e. getting of enclosing class for passed method parameter element.
         *
         * @param element     the element to be used as base
         * @param elementKind the element kind of the enclosing element to search for
         * @return the Element of the specific Element Kind if it could be found, otherwise null
         */
        public static Element getFirstEnclosingElementOfKind(Element element, ElementKind elementKind) {

            if (element == null || elementKind == null) {
                return null;
            }

            Element currentParentElement = element.getEnclosingElement();

            while (currentParentElement != null) {


                if (currentParentElement.getKind() == elementKind) {
                    return currentParentElement;
                }

                // prepare next iteration
                currentParentElement = currentParentElement.getEnclosingElement();

            }

            return null;

        }


    }


    /**
     * Utility cass to access enclosed elements of a {@link Element}.
     */
    public static final class AccessEnclosedElements {

        /**
         * Hidden constructor.
         */
        private AccessEnclosedElements() {
            // does nothing except preventing instantiation
        }

        public static List<? extends VariableElement> getEnclosedFields(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.FIELD);
            return CastElement.castElementList(enclosedElementsOfKind, VariableElement.class);

        }

        /**
         * Get all enclosed methods.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<? extends ExecutableElement> getEnclosedMethods(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.METHOD);
            return CastElement.castElementList(enclosedElementsOfKind, ExecutableElement.class);

        }


        /**
         * Get all enclosed constructors.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<? extends ExecutableElement> getEnclosedConstructors(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.CONSTRUCTOR);
            return CastElement.castElementList(enclosedElementsOfKind, ExecutableElement.class);

        }

        /**
         * Get all enclosed classes.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<? extends TypeElement> getEnclosedTypes(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.CLASS);
            return CastElement.castElementList(enclosedElementsOfKind, TypeElement.class);

        }

        /**
         * Returns all enclosed elements with matching name for passed regular expressions.
         *
         * @param element the element to search within
         * @param name    the name to search for
         * @return the elements with matching name
         */
        public static List<? extends Element> getEnclosedElementsByName(Element element, String... name) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), name);

        }

        /**
         * Returns all enclosed elements with matching name for AT LEAST ONE of the passed regular expressions.
         *
         * @param element     the element to search within
         * @param nameRegexes the regular expressions used for name matching
         * @return the elements with matching name
         * @throws {@link java.util.regex.PatternSyntaxException} if passed pattern is invalid
         */
        public static List<? extends Element> getEnclosedElementsByNameRegex(Element element, String... nameRegexes) {

            List<Element> result = new ArrayList<Element>();

            if (element != null && nameRegexes != null) {

                for (Element enclosedElement : element.getEnclosedElements()) {

                    for (String nameRegex : nameRegexes) {

                        Pattern pattern = Pattern.compile(nameRegex);


                        if (pattern.matcher(enclosedElement.getSimpleName().toString()).matches()) {
                            result.add(enclosedElement);
                            break;
                        }

                    }

                }


            }

            return result;

        }


        /**
         * Returns all enclosed elements with matching name for passed regular expressions.
         *
         * @param element the element to search within
         * @param kind    the kinds to filter
         * @return all enclosed element that are matching one of the passed kinds
         */
        public static List<? extends Element> getEnclosedElementsOfKind(Element element, ElementKind... kind) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return CoreMatchers.BY_ELEMENT_KIND.getFilter().filterByOneOf(element.getEnclosedElements(), kind);

        }


        /**
         * Gets all enclosed elements that are annotated with ALL passed annotations.
         *
         * @param element     the element to search within
         * @param annotations the annotations to filter by
         * @return the enclosed element that are annotated with ALL passed annotations.
         */
        public static List<? extends Element> getEnclosedElementsWithAllAnnotationsOf(Element element, Class<? extends Annotation>... annotations) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return CoreMatchers.BY_ANNOTATION.getFilter().filterByAllOf(element.getEnclosedElements(), annotations);

        }

        /**
         * Gets all enclosed elements that are annotated with AT LEAST ONE of the passed annotations.
         *
         * @param element     the element to search within
         * @param annotations the annotations to filter by
         * @return the enclosed element that are annotated with AT LEAST ONE of the passed annotations.
         */
        public static List<? extends Element> getEnclosedElementsWithAtLeastOneAnnotationOf(Element element, Class<? extends Annotation>... annotations) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return CoreMatchers.BY_ANNOTATION.getFilter().filterByAtLeastOneOf(element.getEnclosedElements(), annotations);

        }

        /**
         * Gets all enclosed elements of the element tree.
         *
         * @param element        the element to get the enclosed elements from
         * @param addRootElement Defines if the passed element should be part of the result
         * @return the flattened enclosed element tree of the passed element
         */
        public static List<? extends Element> flattenEnclosedElementTree(Element element, boolean addRootElement) {
            return flattenEnclosedElementTree(element, addRootElement, Integer.MAX_VALUE);
        }

        /**
         * Gets all enclosed elements of the element tree up to a specific depth.
         *
         * @param element        the element to get the enclosed elements from
         * @param addRootElement Defines if the passed element should be part of the result
         * @param maxDepth       the maximal tree depth for which the elements should be grabbed
         * @return the flattened enclosed element tree of the passed element
         */
        public static List<Element> flattenEnclosedElementTree(Element element, boolean addRootElement, int maxDepth) {
            List<Element> result = new ArrayList<Element>();

            if (element == null) {
                return result;
            }

            if (addRootElement) {
                result.add(element);
            }

            flattenEnclosedElementTree(element, maxDepth, result, -1);

            return result;
        }

        /**
         * Gets all enclosed elements of the element tree.
         *
         * @param element      the element to get the enclosed elements from
         * @param maxDepth     the maximal tree depth for which the elements should be grabbed
         * @param result       the result array to be used
         * @param currentDepth the current depth in the tree
         * @return the flattened enclosed element tree of the passed element
         */
        private static void flattenEnclosedElementTree(Element element, int maxDepth, List<Element> result, int currentDepth) {

            if (currentDepth < maxDepth) {

                // don't add root element to result, this is done in the caller of this method
                if (currentDepth >= 0) {
                    result.add(element);
                }

                currentDepth++;

                for (Element enclosedElement : element.getEnclosedElements()) {
                    flattenEnclosedElementTree(enclosedElement, maxDepth, result, currentDepth);
                }

            }

        }


    }

    /**
     * Utility cass to access enclosed elements of a {@link Element}.
     */
    public static final class AccessTypeHierarchy {

        /**
         * Hidden constructor.
         */
        private AccessTypeHierarchy() {
            // does nothing except preventing instantiation
        }

        /**
         * Gets the direct super TypeElements.
         * The super Type is followed by all implemented interfaces
         *
         * @param typeElement the typeElement to get the direct Super types for.
         * @return an array containing all direct super types or an empty array if no super types can be found (f.e. for Object)
         */
        public static TypeElement[] getDirectSuperTypeElements(TypeElement typeElement) {

            if (typeElement == null) {
                return new TypeElement[0];
            }

            List<? extends TypeMirror> superTypeMirrors = TypeUtils.getTypes().directSupertypes(typeElement.asType());

            List<TypeElement> superTypeElements = new ArrayList<TypeElement>();

            for (TypeMirror superTypeMirror : superTypeMirrors) {

                superTypeElements.add(TypeUtils.TypeRetrieval.getTypeElement(superTypeMirror));

            }

            return superTypeElements.toArray(new TypeElement[superTypeElements.size()]);
        }

        /**
         * Gets all super types of passed typeElement instance.
         * The result array contains all super types in hierachical order (bottom up) followed by all implemented interfaces.
         *
         * @param typeElement the typeElement to get the super types for.
         * @return an array containing all direct super types or an empty array if no super types can be found (f.e. for Object)
         */
        public static TypeElement[] getSuperTypeElements(TypeElement typeElement) {

            // get super types
            TypeElement[] superTypes = getDirectSuperTypeElements(typeElement);


            // this is not that performant, but i guess it's ok since it's only used at compile time.
            List<TypeElement> superTypeElements = new ArrayList<TypeElement>();

            for (TypeElement superType : superTypes) {
                superTypeElements.add(superType);

                List<TypeElement> elementsToBeAdded = Arrays.asList(getSuperTypeElements(superType));

                for (TypeElement elementToBeAdded : elementsToBeAdded) {
                    if (!superTypeElements.contains(elementToBeAdded)) {
                        superTypeElements.add(elementToBeAdded);
                    }
                }
            }

            return superTypeElements.toArray(new TypeElement[superTypeElements.size()]);
        }


        public static TypeElement getDirectSuperTypeElementOfKindType(TypeElement typeElement) {

            FluentElementFilter<TypeElement> fluentElementFilter = FluentElementFilter.createFluentElementFilter(Arrays.asList(getDirectSuperTypeElements(typeElement))).applyFilter(CoreMatchers.IS_CLASS);
            return fluentElementFilter.hasSingleElement() ? fluentElementFilter.getResult().get(0) : null;

        }

        public static TypeElement[] getDirectSuperTypeElementsOfKindInterface(TypeElement typeElement) {

            List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(Arrays.asList(getDirectSuperTypeElements(typeElement))).applyFilter(CoreMatchers.IS_INTERFACE).getResult();
            return resultList.toArray(new TypeElement[resultList.size()]);
        }


        public static TypeElement[] getSuperTypeElementsOfKindType(TypeElement typeElement) {

            List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(Arrays.asList(getSuperTypeElements(typeElement))).applyFilter(CoreMatchers.IS_CLASS).getResult();
            return resultList.toArray(new TypeElement[resultList.size()]);
        }

        public static TypeElement[] getSuperTypeElementsOfKindInterface(TypeElement typeElement) {

            List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(Arrays.asList(getSuperTypeElements(typeElement))).applyFilter(CoreMatchers.IS_INTERFACE).getResult();
            return resultList.toArray(new TypeElement[resultList.size()]);
        }


    }


}
