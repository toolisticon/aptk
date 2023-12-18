package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
         * Needed for handling Module Type until source level is java 9.
         */
        private final static String KIND_MODULE = "MODULE";

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
         * Checks if passed Element instance is of kind module
         *
         * @param e the element to check
         * @return true if passed element is of kind module, otherwise false
         */
        public static boolean isModule(Element e) {
            return (e != null && e.getKind().name().equals(KIND_MODULE));
        }


        /**
         * Checks if passed Element instance is an annotation attribute.
         * Element must be of ElementKind METHOD and enclosing Element must be of ElementKind ANNOTATION.
         *
         * @param e the element to check
         * @return true if passed element represents annotation attribute, otherwise false
         */
        public static boolean isAnnotationAttribute(Element e) {
            return (e != null && isMethod(e) && isAnnotation(e.getEnclosingElement()));
        }

        /**
         * Checks if passed Element instance is a method parameter.
         * Element must be of ElementKind PARAMETER and enclosing Element must be of ElementKind METHOD.
         *
         * @param e the element to check
         * @return true if passed element represents a method parameter, otherwise false
         */
        public static boolean isMethodParameter(Element e) {
            return (e != null && isParameter(e) && isMethod(e.getEnclosingElement()));
        }

        /**
         * Checks if passed Element instance is a constructor parameter.
         * Element must be of ElementKind PARAMETER and enclosing Element must be of ElementKind CONSTRUCTOR.
         *
         * @param e the element to check
         * @return true if passed element represents a constructor parameter, otherwise false
         */
        public static boolean isConstructorParameter(Element e) {
            return (e != null && isParameter(e) && isConstructor(e.getEnclosingElement()));
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
     * Convenience utility class for checking if the kind of {@link Element}.
     */
    public static final class CastElement {

        // look up tables for the different kind of types
        private static final Set<ElementKind> TYPE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.CLASS, ElementKind.INTERFACE, ElementKind.ENUM, ElementKind.ANNOTATION_TYPE);
        private static final Set<ElementKind> TYPE_PARAMETER_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.TYPE_PARAMETER);
        private static final Set<ElementKind> VARIABLE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.PARAMETER, ElementKind.FIELD);
        private static final Set<ElementKind> EXECUTABLE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.CONSTRUCTOR, ElementKind.METHOD);
        private static final Set<ElementKind> PACKAGE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet(ElementKind.PACKAGE);
        private static final Set<String> MODULE_ELEMENT_KIND_LUT = Utilities.convertVarargsToSet("MODULE");


        /**
         * Hidden constructor.
         */
        private CastElement() {
            // does nothing except preventing instantiation
        }


        /**
         * Checks if passed element can be cast to TypeElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to TypeElement, otherwise false
         */
        public static boolean isTypeElement(Element e) {
            return e != null && TYPE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be cast to TypeParameterElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to TypeParameterElement, otherwise false
         */
        public static boolean isTypeParameterElement(Element e) {
            return e != null && TYPE_PARAMETER_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be cast to VariableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to VariableElement, otherwise false
         */
        public static boolean isVariableElement(Element e) {
            return e != null && VARIABLE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be cast to ExecutableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to ExecutableElement, otherwise false
         */
        public static boolean isExecutableElement(Element e) {
            return e != null && EXECUTABLE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be cast to PackageElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to PackageElement, otherwise false
         */
        public static boolean isPackageElement(Element e) {
            return e != null && PACKAGE_ELEMENT_KIND_LUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be cast to ModuleElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to PackageElement, otherwise false
         */
        public static boolean isModuleElement(Element e) {
            return e != null && MODULE_ELEMENT_KIND_LUT.contains(e.getKind().toString());
        }


        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the cast element
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
         * @return the cast element
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
         * @return the cast element
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
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castAnnotationType(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the cast element
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
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static VariableElement castField(Element e) {
            return castToVariableElement(e);
        }

        /**
         * Casts an element to PackageElement.
         *
         * @param e the element to cast
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast to PackageElement
         */
        public static PackageElement castToPackageElement(Element e) {
            return (PackageElement) e;
        }


        /**
         * Casts an element to TypeElement.
         *
         * @param e the element to cast
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast to TypeElement
         */
        public static TypeElement castToTypeElement(Element e) {
            return (TypeElement) e;
        }

        /**
         * Casts an element to TypeParameterElement.
         *
         * @param e the element to cast
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast to TypeParameterElement
         */
        public static TypeParameterElement castToTypeParameterElement(Element e) {
            return (TypeParameterElement) e;
        }

        /**
         * Casts an element to VariableElement.
         *
         * @param e the element to cast
         * @return the cast element
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
         * @return the cast element
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
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static ExecutableElement castMethod(Element e) {
            return castToExecutableElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static ExecutableElement castAnnotationAttribute(Element e) {
            return castToExecutableElement(e);
        }

        /**
         * Casts an element to ExecutableElement.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the cast element
         * @throws ClassCastException if passed Element can't be cast to ExecutableElement
         */
        public static ExecutableElement castToExecutableElement(Element e) {
            return (ExecutableElement) e;
        }

        /**
         * Casts a list of elements to a list of elements.
         *
         * @param elementList  the list to be processed
         * @param typeToCastTo the Element subclass to cast to
         * @param <T>          The expected target element type
         * @return a new list containing all elements of passed elementList
         */
        public static <T extends Element> List<T> castElementList(List<? extends Element> elementList, Class<T> typeToCastTo) {
            return CastElement.<T>castElementList(elementList);
        }

        /**
         * Casts a list of elements to a list of elements.
         *
         * @param elementList the list to be processed
         * @param <T>         the return type
         * @return a new list containing all elements of passed elementList
         */
        public static <T extends Element> List<T> castElementList(List<? extends Element> elementList) {
            List<T> result = new ArrayList<>(elementList.size());
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
            return AptkCoreMatchers.BY_MODIFIER.getValidator().hasAllOf(e, modifier);
        }

        /**
         * Gets the visibility modifier of an element.
         *
         * @param element the element to check
         * @return the visibility modifier of an element or null for package privates
         */
        public static Modifier getVisibilityModifier(Element element) {

            final Modifier[] modifiers = new Modifier[]{Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE};

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

            List<Element> result = new ArrayList<>();

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
         * Gets the first enclosing element parent of specific {@link ElementKind}s.
         * Allows f.e. getting of enclosing class for passed method parameter element.
         *
         * @param element      the element to be used as base
         * @param elementKinds the element kinds of the enclosing element to search for
         * @param <T>          the return type
         * @return the Element of the specific Element Kind if it could be found, otherwise null
         */
        public static <T extends Element> T getFirstEnclosingElementOfKind(Element element, ElementKind... elementKinds) {

            if (element == null || elementKinds == null) {
                return null;
            }

            Element currentParentElement = element.getEnclosingElement();

            while (currentParentElement != null) {

                // loop through all searched ElementKinds
                for (ElementKind elementKind : elementKinds) {
                    if (currentParentElement.getKind() == elementKind) {
                        return (T) currentParentElement;
                    }
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

        public static List<VariableElement> getEnclosedFields(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.FIELD);
            return CastElement.<VariableElement>castElementList(enclosedElementsOfKind);

        }

        /**
         * Get all enclosed methods.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<ExecutableElement> getEnclosedMethods(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.METHOD);
            return CastElement.<ExecutableElement>castElementList(enclosedElementsOfKind);

        }


        /**
         * Get all enclosed constructors.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<ExecutableElement> getEnclosedConstructors(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.CONSTRUCTOR);
            return CastElement.<ExecutableElement>castElementList(enclosedElementsOfKind);

        }

        /**
         * Get all enclosed classes.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<TypeElement> getEnclosedTypes(TypeElement e) {

            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.CLASS);
            return CastElement.<TypeElement>castElementList(enclosedElementsOfKind);

        }

        /**
         * Returns all enclosed elements with matching name for passed regular expressions.
         *
         * @param element the element to search within
         * @param name    the name to search for
         * @return the elements with matching name
         */
        public static List<Element> getEnclosedElementsByName(Element element, String... name) {

            if (element == null) {
                return new ArrayList<>();
            }

            return CastElement.castElementList(AptkCoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), name), Element.class);

        }

        /**
         * Returns all enclosed elements with matching name for AT LEAST ONE of the passed regular expressions.
         *
         * @param element     the element to search within
         * @param nameRegexes the regular expressions used for name matching
         * @return the elements with matching name
         * @throws java.util.regex.PatternSyntaxException if passed pattern is invalid
         */
        public static List<Element> getEnclosedElementsByNameRegex(Element element, String... nameRegexes) {

            List<Element> result = new ArrayList<>();

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
         * @param <T>     The expected enclosed element kind
         * @return all enclosed element that are matching one of the passed kinds
         */
        public static <T extends Element> List<T> getEnclosedElementsOfKind(Element element, ElementKind... kind) {

            if (element == null) {
                return new ArrayList<>();
            }

            return (List<T>) AptkCoreMatchers.BY_ELEMENT_KIND.getFilter().filterByOneOf(element.getEnclosedElements(), kind);

        }


        /**
         * Gets all enclosed elements that are annotated with ALL passed annotations.
         *
         * @param element     the element to search within
         * @param annotations the annotations to filter by
         * @return the enclosed element that are annotated with ALL passed annotations.
         */
        public static List<Element> getEnclosedElementsWithAllAnnotationsOf(Element element, Class<? extends Annotation>... annotations) {

            if (element == null) {
                return new ArrayList<>();
            }

            return (List<Element>) AptkCoreMatchers.BY_ANNOTATION.getFilter().filterByAllOf(element.getEnclosedElements(), annotations);

        }

        /**
         * Gets all enclosed elements that are annotated with AT LEAST ONE of the passed annotations.
         *
         * @param element     the element to search within
         * @param annotations the annotations to filter by
         * @return the enclosed element that are annotated with AT LEAST ONE of the passed annotations.
         */
        public static List<Element> getEnclosedElementsWithAtLeastOneAnnotationOf(Element element, Class<? extends Annotation>... annotations) {

            if (element == null) {
                return new ArrayList<>();
            }

            return (List<Element>) AptkCoreMatchers.BY_ANNOTATION.getFilter().filterByAtLeastOneOf(element.getEnclosedElements(), annotations);

        }

        /**
         * Gets all enclosed elements of the element tree.
         *
         * @param element        the element to get the enclosed elements from
         * @param addRootElement Defines if the passed element should be part of the result
         * @return the flattened enclosed element tree of the passed element
         */
        public static List<Element> flattenEnclosedElementTree(Element element, boolean addRootElement) {
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
            List<Element> result = new ArrayList<>();

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

            List<TypeElement> superTypeElements = new ArrayList<>();

            for (TypeMirror superTypeMirror : superTypeMirrors) {

                superTypeElements.add(TypeUtils.TypeRetrieval.getTypeElement(superTypeMirror));

            }

            return superTypeElements.toArray(new TypeElement[0]);
        }

        /**
         * Gets all super types of passed typeElement instance.
         * The result array contains all super types in hierarchical order (bottom up) followed by all implemented interfaces.
         *
         * @param typeElement the typeElement to get the super types for.
         * @return an array containing all direct super types or an empty array if no super types can be found (f.e. for Object)
         */
        public static TypeElement[] getSuperTypeElements(TypeElement typeElement) {

            // get super types
            TypeElement[] superTypes = getDirectSuperTypeElements(typeElement);


            // this is not that performant, but I guess it's ok since it's only used at compile time.
            List<TypeElement> superTypeElements = new ArrayList<>();

            for (TypeElement superType : superTypes) {
                superTypeElements.add(superType);

                TypeElement[] elementsToBeAdded = getSuperTypeElements(superType);

                for (TypeElement elementToBeAdded : elementsToBeAdded) {
                    if (!superTypeElements.contains(elementToBeAdded)) {
                        superTypeElements.add(elementToBeAdded);
                    }
                }
            }

            return superTypeElements.toArray(new TypeElement[0]);
        }


        public static TypeElement getDirectSuperTypeElementOfKindType(TypeElement typeElement) {

            FluentElementFilter<TypeElement> fluentElementFilter = FluentElementFilter.createFluentElementFilter(Arrays.asList(getDirectSuperTypeElements(typeElement))).applyFilter(AptkCoreMatchers.IS_CLASS);
            return fluentElementFilter.hasSingleElement() ? fluentElementFilter.getResult().get(0) : null;

        }

        public static TypeElement[] getDirectSuperTypeElementsOfKindInterface(TypeElement typeElement) {

            List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(Arrays.asList(getDirectSuperTypeElements(typeElement))).applyFilter(AptkCoreMatchers.IS_INTERFACE).getResult();
            return resultList.toArray(new TypeElement[0]);
        }


        public static TypeElement[] getSuperTypeElementsOfKindType(TypeElement typeElement) {

            List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(Arrays.asList(getSuperTypeElements(typeElement))).applyFilter(AptkCoreMatchers.IS_CLASS).getResult();
            return resultList.toArray(new TypeElement[0]);
        }

        public static TypeElement[] getSuperTypeElementsOfKindInterface(TypeElement typeElement) {

            List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(Arrays.asList(getSuperTypeElements(typeElement))).applyFilter(AptkCoreMatchers.IS_INTERFACE).getResult();
            return resultList.toArray(new TypeElement[0]);
        }


    }

    public static class ForExecutableElements {

        /**
         * Hidden constructor.
         */
        private ForExecutableElements() {

        }


        /**
         * Gets the method signature String for an ExecutableElement.
         * <p>
         * This is useful for implementing of interfaces in generated classes.
         * <p>
         * This method works perfectly well for ExecutableElements of classes and interfaces in compilation,
         * but will have some limitations for precompiled classes.
         * <p>
         * Keep in mind that javac doesn't store parameter names in bytecode out of the box.
         * So passing in an ExecutableElement of a precompiled class will have arg0,arg1,.. as parameter names.
         * This can be changed if class is compiled with "-parameters" compiler option.
         *
         * @param executableElement the Method to get the method signature for
         * @return the method signature
         */
        static String getMethodSignature(ExecutableElement executableElement) {

            StringBuilder builder = new StringBuilder();

            // Add throws if present
            if (!executableElement.getTypeParameters().isEmpty()) {
                builder.append("<");
                builder.append(executableElement.getTypeParameters().stream().map(
                        tp -> tp.toString() + " extends " + TypeMirrorWrapper.wrap(tp.getBounds().get(0)).getTypeDeclaration()).collect(Collectors.joining(", "))
                );
                builder.append("> ");
            }


            // return type and method name
            TypeMirrorWrapper wrappedReturnType = TypeMirrorWrapper.wrap(executableElement.getReturnType());
            builder.append(wrappedReturnType.getTypeDeclaration()).append(" ").append(executableElement.getSimpleName());

            // add parameters
            builder.append("(");
            builder.append(executableElement.getParameters().stream().map(
                    element -> TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration() + " " + element.getSimpleName().toString()).collect(Collectors.joining(", "))
            );
            builder.append(")");

            // Add throws if present
            if (!executableElement.getThrownTypes().isEmpty()) {
                builder.append(" throws ");
                builder.append(executableElement.getThrownTypes().stream().map(tm -> TypeMirrorWrapper.wrap(tm).getSimpleName()).collect(Collectors.joining(", ")));
            }

            return builder.toString();
        }

    }

    static List<String> getImportsNeededByMethodsSignature() {
        return null;
    }


}
