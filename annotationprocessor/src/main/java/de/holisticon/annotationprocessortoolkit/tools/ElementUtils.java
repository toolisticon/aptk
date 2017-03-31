package de.holisticon.annotationprocessortoolkit.tools;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Utility class which helps to handle different {@link Element} related tasks.
 */
public final class ElementUtils {

    private static final ElementUtils INSTANCE = new ElementUtils();

    /**
     * Hidden constructor that prevents instantiation.
     */
    private ElementUtils() {

    }

    /**
     * Checks if passed Element instance is of kind enum.
     *
     * @param e the element to check
     * @return true if passed element is of kind enum, otherwise false
     */
    public boolean isEnum(Element e) {
        return isOfKind(e, ElementKind.ENUM);
    }

    /**
     * Checks if passed Element instance is of kind class.
     *
     * @param e the element to check
     * @return true if passed element is of kind class, otherwise false
     */
    public boolean isClass(Element e) {
        return isOfKind(e, ElementKind.CLASS);
    }

    /**
     * Checks if passed Element instance is of kind interface.
     *
     * @param e the element to check
     * @return true if passed element is of kind enum, otherwise false
     */
    public boolean isInterface(Element e) {
        return isOfKind(e, ElementKind.INTERFACE);
    }

    /**
     * Checks if passed Element instance is of kind method.
     *
     * @param e the element to check
     * @return true if passed element is of kind method, otherwise false
     */
    public boolean isMethod(Element e) {
        return isOfKind(e, ElementKind.METHOD);
    }


    /**
     * Checks if passed Element instance is of kind parameter.
     *
     * @param e the element to check
     * @return true if passed element is of kind parameter, otherwise false
     */
    public boolean isParameter(Element e) {
        return isOfKind(e, ElementKind.PARAMETER);
    }

    /**
     * Checks if passed Element instance is of kind constructor.
     *
     * @param e the element to check
     * @return true if passed element is of kind constructor, otherwise false
     */
    public boolean isConstructor(Element e) {
        return isOfKind(e, ElementKind.CONSTRUCTOR);
    }

    /**
     * Checks if passed Element instance is of kind field.
     *
     * @param e the element to check
     * @return true if passed element is of kind field, otherwise false
     */
    public boolean isField(Element e) {
        return isOfKind(e, ElementKind.FIELD);
    }

    /**
     * Checks if passed Element instance is of a specific kind.
     *
     * @param e    the element to check
     * @param kind the kind to check for
     * @return true if passed element is of the passed kind, otherwise false
     */
    public boolean isOfKind(Element e, ElementKind kind) {
        return kind != null && e != null ? kind.equals(e.getKind()) : false;
    }


    /**
     * Casts an element.
     * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
     *
     * @param e the element to cast
     * @return the casted element
     * @throws ClassCastException if passed Element can't be cast
     */
    public TypeElement castClass(Element e) {
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
    public TypeElement castInterface(Element e) {
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
    public TypeElement castEnum(Element e) {
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
    public VariableElement castParameter(Element e) {
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
    public VariableElement castField(Element e) {
        return castToVariableElement(e);
    }

    /**
     * Casts an element to TypeElement.
     *
     * @param e the element to cast
     * @return the casted element
     * @throws ClassCastException if passed Element can't be cast to TypeElement
     */
    public TypeElement castToTypeElement(Element e) {
        return (TypeElement) e;
    }

    /**
     * Casts an element to VariableElement.
     *
     * @param e the element to cast
     * @return the casted element
     * @throws ClassCastException if passed Element can't be cast to TypeElement
     */
    public VariableElement castToVariableElement(Element e) {
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
    public ExecutableElement castConstructor(Element e) {
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
    public ExecutableElement castMethod(Element e) {
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
    public ExecutableElement castToExecutableElement(Element e) {
        return (ExecutableElement) e;
    }


    /**
     * Check if passed Element has public Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the public modifier, otherwise false
     */
    public boolean hasPublicModifier(Element e) {
        return hasModifiers(e, Modifier.PUBLIC);
    }

    /**
     * Check if passed Element has protected Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the protected modifier, otherwise false
     */
    public boolean hasProtectedModifier(Element e) {
        return hasModifiers(e, Modifier.PROTECTED);
    }

    /**
     * Check if passed Element has private Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the private modifier, otherwise false
     */
    public boolean hasPrivateModifier(Element e) {
        return hasModifiers(e, Modifier.PRIVATE);
    }

    /**
     * Check if passed Element has abstract Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the abstract modifier, otherwise false
     */
    public boolean hasAbstractModifier(Element e) {
        return hasModifiers(e, Modifier.ABSTRACT);
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
        return hasModifiers(e, Modifier.DEFAULT);
    }
    */

    /**
     * Check if passed Element has static Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the static modifier, otherwise false
     */
    public boolean hasStaticModifier(Element e) {
        return hasModifiers(e, Modifier.STATIC);
    }

    /**
     * Check if passed Element has final Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the final modifier, otherwise false
     */
    public boolean hasFinalModifier(Element e) {
        return hasModifiers(e, Modifier.FINAL);
    }

    /**
     * Check if passed Element has transient Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the transient modifier, otherwise false
     */
    public boolean hasTransientModifier(Element e) {
        return hasModifiers(e, Modifier.TRANSIENT);
    }

    /**
     * Check if passed Element has volatile Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the volatile modifier, otherwise false
     */
    public boolean hasVolatileModifier(Element e) {
        return hasModifiers(e, Modifier.VOLATILE);
    }

    /**
     * Check if passed Element has synchronized Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the synchronized modifier, otherwise false
     */
    public boolean hasSynchronizedModifier(Element e) {
        return hasModifiers(e, Modifier.SYNCHRONIZED);
    }

    /**
     * Check if passed Element has native Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the native modifier, otherwise false
     */
    public boolean hasNativeModifier(Element e) {
        return hasModifiers(e, Modifier.NATIVE);
    }

    /**
     * Check if passed Element has strictfp Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the strictfp modifier, otherwise false
     */
    public boolean hasStrictfpModifier(Element e) {
        return hasModifiers(e, Modifier.STRICTFP);
    }

    /**
     * Check if passed Element has passed Modifiers.
     *
     * @param e         the element to check
     * @param modifiers the modifiers to check for
     * @return true if the passed element has all of the passed modifiers, otherwise false
     */
    public boolean hasModifiers(Element e, Modifier... modifiers) {

        if (e == null || modifiers == null) {
            return true;
        }

        for (Modifier modifier : modifiers) {

            if (!e.getModifiers().contains(modifier)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if passed Element has not passed Modifiers.
     *
     * @param e         the element to check
     * @param modifiers the modifiers to check for
     * @return true if the passed element has none ofthe passed modifiers, otherwise false
     */
    public boolean hasNotModifiers(Element e, Modifier... modifiers) {

        if (e == null || modifiers == null) {
            return true;
        }

        for (Modifier modifier : modifiers) {

            if (e.getModifiers().contains(modifier)) {
                return false;
            }
        }

        return true;
    }


    public List<VariableElement> getEnclosedFields(TypeElement e) {
        List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.FIELD);
        return castElementList(enclosedElementsOfKind, VariableElement.class);
    }

    /**
     * Get all enclosed methods.
     *
     * @param e the element to search within
     * @return all methods of the passed element
     */
    public List<ExecutableElement> getEnclosedMethods(TypeElement e) {
        List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.METHOD);
        return castElementList(enclosedElementsOfKind, ExecutableElement.class);
    }


    public <T extends Element> List<T> castElementList(List<? extends Element> elementList, Class<T> typeToCastTo) {
        List<T> result = new ArrayList<T>(elementList.size());
        for (Element enclosedElement : elementList) {
            result.add((T) enclosedElement);
        }
        return result;
    }


    /**
     * Returns all enclosed elements with matching name for passed regular expression.
     *
     * @param element the element to search within
     * @param name    the name to search for
     * @return the elements with matching name
     */
    public List<? extends Element> getEnclosedElementsByName(Element element, String... name) {

        return filterElementListByName(element.getEnclosedElements(), name);

    }

    /**
     * Returns all enclosed elements with matching name for passed regular expression.
     *
     * @param element   the element to search within
     * @param nameRegex the regular expression for name
     * @return the elements with matching name
     * @throws PatternSyntaxException if passed pattern is invalid
     */
    public List<Element> getEnclosedElementsByNameRegex(Element element, String nameRegex) {
        List<Element> result = new ArrayList<Element>();

        if (element != null && nameRegex != null) {

            Pattern pattern = Pattern.compile(nameRegex);

            for (Element enclosedElement : element.getEnclosedElements()) {

                if (pattern.matcher(enclosedElement.getSimpleName().toString()).matches()) {
                    result.add(enclosedElement);
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
    public List<? extends Element> getEnclosedElementsOfKind(Element element, ElementKind... kind) {

        return filterElementListByKind(element.getEnclosedElements(), kind);

    }


    /**
     * Gets all enclosed elements that are annotated with ALL passed annotations
     *
     * @param element     the element to search within
     * @param annotations the annotations to filter by
     * @return the enclosed element that are annotated with ALL passed annotations.
     */
    public List<? extends Element> getEnclosedElementsWithAnnotation(Element element, Class<? extends Annotation>... annotations) {

        return filterElementListByAnnotation(element.getEnclosedElements(), annotations);

    }


    /**
     * Filter passed list of elements by modifiers.
     * Returned list will contain all Elements with have ALL passed modifiers
     *
     * @param elementList the Element list to filter
     * @param modifiers   the Modifiers to filter by
     * @param <T>
     * @return the filtered list with elements that have all passed modifiers
     */
    public <T extends Element> List<T> filterElementListByModifier(List<T> elementList, Modifier... modifiers) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && modifiers != null) {

            outer:
            for (T element : elementList) {

                for (Modifier modifier : modifiers) {
                    if (!element.getModifiers().contains(modifier)) {
                        continue outer;
                    }
                }

                result.add(element);
            }

        }

        return result;
    }


    /**
     * Filter passed list of elements by annotation.
     * Returned list will contain all Elements that are annotated with ALL of the passed annotations
     *
     * @param elementList the Element list to filter
     * @param annotations the annotations to filter by
     * @param <T>
     * @return the filtered list with elements that have matching names
     */
    public <T extends Element> List<T> filterElementListByAnnotation(List<T> elementList, Class<? extends Annotation>... annotations) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && annotations != null) {

            outer:
            for (T element : elementList) {

                for (Class<? extends Annotation> annotation : annotations) {
                    if (element.getAnnotation(annotation) == null) {
                        continue outer;
                    }
                }
                result.add(element);

            }

        }

        return result;
    }

    /**
     * Filter passed list of elements by annotation.
     * Returned list will contain Elements that are not annotated with ALL of the passed annotations.
     *
     * @param elementList the Element list to filter
     * @param annotations the annotations to filter by
     * @param <T>
     * @return the filtered list with elements that are annotatetd with none of the no matching names
     */
    public <T extends Element> List<T> inverseFilterElementListByAnnotation(List<T> elementList, Class<? extends Annotation>... annotations) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && annotations != null) {

            outer:
            for (T element : elementList) {

                for (Class<? extends Annotation> annotation : annotations) {
                    if (element.getAnnotation(annotation) != null) {
                        continue outer;
                    }
                }
                result.add(element);

            }

        }

        return result;
    }


    /**
     * Filter passed list of elements by names.
     * Returned list will contain all Elements with match ONE of the passed names
     *
     * @param elementList the Element list to filter
     * @param names       the names to filter by
     * @param <T>
     * @return the filtered list with elements that have matching names
     */
    public <T extends Element> List<T> filterElementListByName(List<T> elementList, String... names) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && names != null) {

            for (T element : elementList) {

                for (String name : names) {
                    if (name != null && name.equals(element.getSimpleName().toString())) {
                        result.add(element);
                        break;
                    }
                }

            }

        }

        return result;
    }

    /**
     * Filter passed list of elements by names.
     * Returned list will contain Elements that have no matching names.
     *
     * @param elementList the Element list to filter
     * @param names       the names to filter by
     * @param <T>
     * @return the filtered list with elements that have no matching names
     */
    public <T extends Element> List<T> inverseFilterElementListByName(List<T> elementList, String... names) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && names != null) {

            outer:
            for (T element : elementList) {

                for (String name : names) {
                    if (name != null && name.equals(element.getSimpleName().toString())) {
                        // continue with next element
                        continue outer;
                    }
                }
                result.add(element);

            }

        }

        return result;
    }


    /**
     * Returns all enclosed elements with matching name for ONE of the passed regular expressions.
     *
     * @param elementList        the element to search within
     * @param regularExpressions the regular expressions for name
     * @return the elements with matching name
     * @throws PatternSyntaxException if passed pattern is invalid
     */
    public <T extends Element> List<T> filterElementsByNameWithRegularExpression(List<T> elementList, String... regularExpressions) {
        List result = new ArrayList();

        if (elementList != null && regularExpressions != null) {

            for (Element enclosedElement : elementList) {

                for (String regularExpression : regularExpressions) {

                    if (regularExpression != null) {

                        Pattern pattern = Pattern.compile(regularExpression);

                        if (pattern.matcher(enclosedElement.getSimpleName().toString()).matches()) {
                            result.add(enclosedElement);
                            break;
                        }

                    }

                }
            }

        }

        return result;
    }

    /**
     * Filter passed list of elements by kind.
     * Returned list will contain all Elements that are matching ONE of the passed kinds.
     *
     * @param elementList the Element list to filter
     * @param kinds       the kinds to filter by
     * @param <T>
     * @return the filtered list with elements that have matching kinds
     */
    public <T extends Element> List<T> filterElementListByKind(List<T> elementList, ElementKind... kinds) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && kinds != null) {

            for (T element : elementList) {

                for (ElementKind kind : kinds) {
                    if (kind != null && kind.equals(element.getKind())) {
                        result.add(element);
                        break;
                    }
                }

            }

        }

        return result;
    }

    /**
     * Filter passed list of elements by kind.
     * Returned list will contain all Elements that are matching NONE of the passed kinds.
     *
     * @param elementList the Element list to filter
     * @param kinds       the kinds to filter by
     * @param <T>
     * @return the filtered list with elements that have no matching kind
     */
    public <T extends Element> List<T> inverseFilterElementListByKind(List<T> elementList, ElementKind... kinds) {
        List<T> result = new ArrayList<T>();

        if (elementList != null && kinds != null) {

            outer:
            for (T element : elementList) {

                for (ElementKind kind : kinds) {
                    if (kind != null && kind.equals(element.getKind())) {
                        // continue with next element
                        continue outer;
                    }
                }
                result.add(element);

            }

        }

        return result;
    }

    public boolean isAnnotatedWith(Element e, Class<? extends Annotation> a) {
        return e == null || a == null ? false : e.getAnnotation(a) != null;
    }


    /**
     * Static way to get the element utils
     *
     * @return the element utils instance
     */
    public static ElementUtils getElementUtils() {
        return INSTANCE;
    }
}
