package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.command.impl.GetAttributesCommand;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class to handle bean related tasks.
 */
public final class BeanUtils {

    final static String[] GETTER_PREFIXES = {"get", "is", "has"};
    final static String[] SETTER_PREFIXES = {"set"};

    /**
     * Class to store attribute related data.
     */
    public static class AttributeResult {

        private VariableElementWrapper field;


        private String setterMethodName;
        private String getterMethodName;


        public VariableElementWrapper getField() {
            return field;
        }

        public void setField(VariableElementWrapper field) {
            this.field = field;
        }

        public boolean hasGetter() {
            return getterMethodName != null;
        }

        public boolean hasSetter() {
            return setterMethodName != null;
        }

        public String getSetterMethodName() {
            return setterMethodName;
        }

        public void setSetterMethodName(String setterMethodName) {
            this.setterMethodName = setterMethodName;
        }

        public String getGetterMethodName() {
            return getterMethodName;
        }

        public void setGetterMethodName(String getterMethodName) {
            this.getterMethodName = getterMethodName;
        }

        public String getFieldName() {
            return field.getSimpleName();
        }

        public TypeMirrorWrapper getFieldTypeMirror() {
            return field.asType();
        }

        public boolean isValidAttribute() {
            return this.hasGetter() && this.hasSetter();
        }

    }


    /**
     * Hidden constructor
     */
    private BeanUtils() {

    }

    /**
     * Checks if typeElement has a sole default no-args constructor.
     * <p>
     * Internally calls isDefaultNoargConstructor.
     * See that method description for detailed overview of checked criteria.
     *
     * @param typeElement the type element
     * @return true
     */
    public static boolean hasDefaultNoargsConstructor(TypeElement typeElement) {

        List<ExecutableElement> constructors = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_CONSTRUCTOR)
                .getResult();

        // check for number of constructors
        if (constructors.size() != 1) {
            return false;
        }

        return isDefaultNoargConstructor(constructors.get(0));


    }


    /**
     * Checks whether an ExecutableElement is a default noargs constructor.
     * <p>
     * Checks if
     * <ul>
     * <li>executable element is public constructor without parameters</li>
     * <li>constructor is sole constructor</li>
     * <li>constructor contains just a super(); statement in body</li>
     * <li>constructor has no thrown types declarations</li>
     * <li>constructor has same visibility modifier like it's type</li>
     * </ul>
     *
     * @param element The executable element to check
     * @return true if passes executable element is constructor, otherwise false
     */
    public static boolean isDefaultNoargConstructor(ExecutableElement element) {

        // first check element
        if (!FluentElementValidator.createFluentElementValidator(element)
                .applyValidator(AptkCoreMatchers.BY_ELEMENT_KIND).hasOneOf(ElementKind.CONSTRUCTOR)
                .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC)
                .applyValidator(AptkCoreMatchers.HAS_NO_PARAMETERS)
                .applyValidator(AptkCoreMatchers.HAS_NO_THROWN_TYPES)
                .justValidate()) {
            return false;
        }


        TypeElement typeElementFilter = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(element, ElementKind.CLASS);

        // check for number of constructors
        if (!FluentElementFilter.createFluentElementFilter(typeElementFilter.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_CONSTRUCTOR).hasSingleElement()) {
            return false;
        }

        // must have same visibility like class
        if (ElementUtils.CheckModifierOfElement.getVisibilityModifier(typeElementFilter) != ElementUtils.CheckModifierOfElement.getVisibilityModifier(element)) {
            return false;
        }

        /*-
        // now check statements of constructor
        List<? extends StatementTree> statements = ProcessingEnvironmentUtils.getTrees().getTree(element).getBody().getStatements();

        if (statements.size() != 1) {
            return false;
        }

        return Pattern.compile("^\\s*super\\(\\);?\\s*$").matcher(statements.get(0).toString()).matches();
        */
        return true;
    }

    public static boolean isAttribute(VariableElement field) {

        return checkHasSetter(field) && checkHasGetter(field);

    }

    public static AttributeResult[] getAttributesWithInheritance(TypeElement typeElement, TypeMirrorWrapper... typeArguments) {


        // this doesn't support getting generic fields in parent classes
        List<AttributeResult> resultList = new ArrayList<>(Arrays.asList(GetAttributesCommand.createCommand().execute(typeElement)));

        // process super types
        for (TypeElement superTypeElement : ElementUtils.AccessTypeHierarchy.getSuperTypeElementsOfKindType(typeElement)) {
            TypeElementWrapper wrappedSuperTypeElement = TypeElementWrapper.wrap(superTypeElement);
            Map<String, TypeMirrorWrapper> resolvedTypeArgumentMap = new HashMap<>();
            if (wrappedSuperTypeElement.hasTypeParameters()) {
                List<TypeMirrorWrapper> resolvedTypeArgumentTypes = InterfaceUtils.getResolvedTypeArgumentOfSuperTypeOrInterface(TypeElementWrapper.wrap(typeElement), TypeMirrorWrapper.wrap(superTypeElement), typeArguments);
                resolvedTypeArgumentMap = InterfaceUtils.mapTypeVars(wrappedSuperTypeElement, resolvedTypeArgumentTypes.toArray(new TypeMirrorWrapper[0]));
            }
            resultList.addAll(Arrays.asList(GetAttributesCommand.createCommand(resolvedTypeArgumentMap).execute(superTypeElement)));
        }

        return resultList.toArray(new AttributeResult[0]);

    }

    public static AttributeResult[] getAttributes(TypeElement typeElement) {


        if (typeElement == null) {
            return new AttributeResult[0];
        }

        List<VariableElement> fields = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_FIELD)
                .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByNoneOf(Modifier.STATIC)
                .getResult();

        List<AttributeResult> result = new ArrayList<>();

        for (VariableElement field : fields) {

            // just add those fields with both getters and setters
            Optional<String> getterMethodName = BeanUtils.getGetterMethodName(field);
            Optional<String> setterMethodName = BeanUtils.getSetterMethodName(field);
            if (getterMethodName.isPresent() && setterMethodName.isPresent()) {

                AttributeResult attributeResult = new AttributeResult();
                attributeResult.setField(VariableElementWrapper.wrap(field));
                attributeResult.setGetterMethodName(getterMethodName.get());
                attributeResult.setSetterMethodName(setterMethodName.get());
                result.add(attributeResult);
            }

        }

        return result.toArray(new AttributeResult[0]);

    }


    /**
     * Checks if passed field has getter method.
     * If getter method cannot be found it will check if there are lombok annotations which will triger generation of it.
     *
     * @param field the VariableElement of a field
     * @return true if getter method is present or to be generated by lombok
     */
    public static boolean checkHasGetter(VariableElement field) {

        if (field == null || field.getKind() != ElementKind.FIELD || AptkCoreMatchers.BY_MODIFIER.getMatcher().checkForMatchingCharacteristic(field, Modifier.STATIC)) {
            return false;
        }

        TypeElement typeElement = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS);


        return checkHasGetterMethod(field, typeElement);

    }

    /**
     * Checks if passed field has setter method.
     * If setter method  cannot be found it will check if there are lombok annotations which will triger generation of it.
     *
     * @param field the VariableElement of a field
     * @return true if getter method is present or to be generated by lombok
     */
    public static boolean checkHasSetter(VariableElement field) {

        if (field == null || field.getKind() != ElementKind.FIELD) {
            return false;
        }

        TypeElement typeElement = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS);


        return checkLombokSetterAnnotationOnType(typeElement)
                || checkLombokSetterAnnotationOnField(field)
                || checkHasSetterMethod(field, typeElement)
                ;

    }


    /**
     * Get the getters method name.
     *
     * @param field The field
     * @return the getters method name or null if field has no getter
     */
    public static Optional<String> getGetterMethodName(VariableElement field) {

        if (field == null || field.getKind() != ElementKind.FIELD) {
            return Optional.empty();
        }

        TypeElement typeElement = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS);

        ExecutableElement getterMethod = getGetterMethod(field, typeElement);

        if (getterMethod != null) {
            return Optional.of(getterMethod.getSimpleName().toString());
        }

        return Optional.empty();
    }

    /**
     * Get setter name for a field.
     *
     * @param field the fields VariableElement
     * @return the name of the setter method
     */
    public static Optional<String> getSetterMethodName(VariableElement field) {
        if (field == null || field.getKind() != ElementKind.FIELD) {
            return Optional.empty();
        }

        return checkHasSetter(field) ? Optional.of(getPrefixedName("set", field.getSimpleName().toString())) : Optional.empty();
    }

    /**
     * Checks if a field has a setter method or if it will be created by lombok
     *
     * @param field       the fields VariableElement
     * @param typeElement the TypeElement
     * @return true if field has a getter method, otherwise false
     */
    static boolean checkHasGetterMethod(VariableElement field, TypeElement typeElement) {

        return getGetterMethod(field, ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS)) != null;

    }

    static ExecutableElement getGetterMethod(VariableElement field, TypeElement typeElement) {
        List<ExecutableElement> result = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_METHOD)
                .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC)
                .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByNoneOf(Modifier.ABSTRACT, Modifier.STATIC)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(getPossibleGetterOrSetterNames(field, GETTER_PREFIXES))
                .applyFilter(AptkCoreMatchers.HAS_NO_PARAMETERS)
                .applyFilter(AptkCoreMatchers.BY_RETURN_TYPE_MIRROR).filterByOneOf(field.asType())
                .getResult();

        return result.size() >= 1 ? result.get(0) : null;
    }

    static boolean checkHasSetterMethod(VariableElement field, TypeElement typeElement) {


        return getSetterMethod(field, typeElement) != null;


    }

    static ExecutableElement getSetterMethod(VariableElement field, TypeElement typeElement) {

        TypeMirror[] parameters = {field.asType()};

        List<ExecutableElement> result = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_METHOD)
                .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC)
                .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByNoneOf(Modifier.ABSTRACT, Modifier.STATIC)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(getPossibleGetterOrSetterNames(field, SETTER_PREFIXES))
                .applyFilter(AptkCoreMatchers.HAS_VOID_RETURN_TYPE)
                .applyFilter(AptkCoreMatchers.BY_PARAMETER_TYPE_MIRROR).filterByOneOf(parameters)
                .getResult();

        return result.size() >= 1 ? result.get(0) : null;
    }

    static String[] getPossibleGetterOrSetterNames(VariableElement field, String[] prefixes) {
        String[] result = new String[prefixes.length];

        for (int i = 0; i < prefixes.length; i++) {

            result[i] = getPrefixedName(prefixes[i], field.getSimpleName().toString());

        }

        return result;
    }

    /**
     * Method to create a prefixed camel cased String
     *
     * @param name   the name to be prefixed
     * @param prefix the prefix
     * @return the camel cased string
     */
    public static String getPrefixedName(String prefix, String name) {
        return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static boolean checkLombokSetterAnnotationOnType(TypeElement typeElement) {

        return AnnotationUtils.getAnnotationMirror(typeElement, "lombok.Setter") != null;

    }

    public static boolean checkLombokSetterAnnotationOnField(VariableElement variableElement) {

        return AnnotationUtils.getAnnotationMirror(variableElement, "lombok.Setter") != null;

    }


}
