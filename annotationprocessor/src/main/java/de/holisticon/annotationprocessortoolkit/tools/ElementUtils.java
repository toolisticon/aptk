package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.internal.Utilities;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validators;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
    public final static class CheckKindOfElement {

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
         * Checks if passed Element instance is of a specific kind.
         *
         * @param e    the element to check
         * @param kind the kind to check for
         * @return true if passed element is of the passed kind, otherwise false
         */
        public static boolean isOfKind(Element e, ElementKind kind) {
            return kind != null && e != null ? kind.equals(e.getKind()) : false;
        }

    }

    /**
     * Convenience utility class for checking if the kind of an {@link Element}.
     */
    public final static class CastElement {

        // look up tables for the different kind of types
        private final static Set<ElementKind> typeElementKindLUT = Utilities.convertVarargsToSet(ElementKind.CLASS, ElementKind.INTERFACE, ElementKind.ENUM);
        private final static Set<ElementKind> variableElementKindLUT = Utilities.convertVarargsToSet(ElementKind.PARAMETER, ElementKind.FIELD);
        private final static Set<ElementKind> executableElementKindLUT = Utilities.convertVarargsToSet(ElementKind.CONSTRUCTOR, ElementKind.METHOD);

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
            return e != null && typeElementKindLUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to VariableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to VariableElement, otherwise false
         */
        public static boolean isVariableElement(Element e) {
            return e != null && variableElementKindLUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to ExecutableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to ExecutableElement, otherwise false
         */
        public static boolean isExecutableElement(Element e) {
            return e != null && executableElementKindLUT.contains(e.getKind());
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
    public final static class CheckModifierOfElement {

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
            return Validators.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, modifier);
        }

    }

    /**
     * Utility cass to access enclosed elements of a {@link Element}.
     */
    public final static class AccessEnclosedElements {

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
         * Returns all enclosed elements with matching name for passed regular expression.
         *
         * @param element the element to search within
         * @param name    the name to search for
         * @return the elements with matching name
         */
        public static List<? extends Element> getEnclosedElementsByName(Element element, String... name) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), name);

        }

        /**
         * Returns all enclosed elements with matching name for AT LEAST ONE of the passed regular expressions.
         *
         * @param element     the element to search within
         * @param nameRegexes the regular expressions used for name matching
         * @return the elements with matching name
         * @throws PatternSyntaxException if passed pattern is invalid
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
         * Returns all enclosed elements with matching name for passed regular expression.
         *
         * @param element the element to search within
         * @param kind    the kinds to filter
         * @return all enclosed element that are matching one of the passed kinds
         */
        public static List<? extends Element> getEnclosedElementsOfKind(Element element, ElementKind... kind) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return Filters.ELEMENT_KIND_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), kind);

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

            return Filters.ANNOTATION_FILTER.getFilter().filterByAllOf(element.getEnclosedElements(), annotations);

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

            return Filters.ANNOTATION_FILTER.getFilter().filterByAtLeastOneOf(element.getEnclosedElements(), annotations);

        }
    }


}
