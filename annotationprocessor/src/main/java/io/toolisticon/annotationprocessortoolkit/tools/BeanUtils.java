package io.toolisticon.annotationprocessortoolkit.tools;

import com.sun.source.tree.StatementTree;
import io.toolisticon.annotationprocessortoolkit.tools.command.impl.GetAttributesCommand;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class to handle bean related tasks
 */
public final class BeanUtils {

    protected final static String[] GETTER_PREFIXES = {"get", "is", "has"};
    protected final static String[] SETTER_PREFIXES = {"set"};

    /**
     * Class to store attribute related data.
     */
    public static class AttributeResult {

        private VariableElement field;


        private String setterMethodName;
        private String getterMethodName;


        public VariableElement getField() {
            return field;
        }

        public void setField(VariableElement field) {
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
            return field.getSimpleName().toString();
        }

        public TypeMirror getFieldTypeMirror() {
            return field.asType();
        }

        public TypeElement getFieldTypeElement() {
            return TypeUtils.TypeRetrieval.getTypeElement(getFieldTypeMirror());
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
     * Checks if typeElement has a sole default noargs constructor.
     * <p/>
     * Internally calls isDefaultNoargConstructor.
     * See that method description for detailed overview of checked criteria.
     *
     * @param typeElement
     * @return true
     */
    public static boolean hasDefaultNoargsConstructor(TypeElement typeElement) {

        List<ExecutableElement> constructors = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                .getResult();

        // check for number of constructors
        if (constructors.size() != 1) {
            return false;
        }

        return isDefaultNoargConstructor(constructors.get(0));


    }


    /**
     * Checks whether an ExecutableElement is a default noargs constructor.
     * <p/>
     * Checks if
     * <ul>
     * <li>executable element is public constructor without parameters</li>
     * <li>constructor is sole constructor</li>
     * <li>constructor contains just a super(); statement in body</li>
     * <li>constructor has no thrown types declarations</li>
     * <li>constructor has same visibility modifier like it's type</li>
     * </ul>
     *
     * @param element
     * @return
     */
    public static boolean isDefaultNoargConstructor(ExecutableElement element) {

        // first check element
        if (!FluentElementValidator.createFluentElementValidator(element)
                .applyValidator(CoreMatchers.BY_ELEMENT_KIND).hasOneOf(ElementKind.CONSTRUCTOR)
                .applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC)
                .applyValidator(CoreMatchers.HAS_NO_PARAMETERS)
                .applyValidator(CoreMatchers.HAS_NO_THROWN_TYPES)
                .justValidate()) {
            return false;
        }


        TypeElement typeElementFilter = (TypeElement) ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(element, ElementKind.CLASS);

        // check for number of constructors
        if (!FluentElementFilter.createFluentElementFilter(typeElementFilter.getEnclosedElements())
                .applyFilter(CoreMatchers.IS_CONSTRUCTOR).hasSingleElement()) {
            return false;
        }

        // must have same visibility like class
        if (ElementUtils.CheckModifierOfElement.getVisibilityModifier(typeElementFilter) != ElementUtils.CheckModifierOfElement.getVisibilityModifier(element)) {
            return false;
        }

        // now check statements of constructor
        List<? extends StatementTree> statements = ProcessingEnvironmentUtils.getTrees().getTree(element).getBody().getStatements();

        if (statements.size() != 1) {
            return false;
        }

        return Pattern.compile("^\\s*super\\(\\)[;]{0,1}\\s*$").matcher(statements.get(0).toString()).matches();

    }

    public static boolean isAttribute(VariableElement field) {

        return checkHasSetter(field) && checkHasGetter(field);

    }

    public static AttributeResult[] getAttributesWithInheritance(TypeElement typeElement) {
        List<AttributeResult> resultList = new ArrayList<AttributeResult>();
        resultList.addAll(Arrays.asList(GetAttributesCommand.INSTANCE.execute(typeElement)));

        // process super types
        for (TypeElement superTypeElement : ElementUtils.AccessTypeHierarchy.getSuperTypeElementsOfKindType(typeElement)) {
            resultList.addAll(Arrays.asList(GetAttributesCommand.INSTANCE.execute(superTypeElement)));
        }

        return resultList.toArray(new AttributeResult[resultList.size()]);

    }

    public static AttributeResult[] getAttributes(TypeElement typeElement) {


        if (typeElement == null) {
            return new AttributeResult[0];
        }

        List<VariableElement> fields = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(CoreMatchers.IS_FIELD)
                .applyFilter(CoreMatchers.BY_MODIFIER).filterByNoneOf(Modifier.STATIC)
                .getResult();

        List<AttributeResult> result = new ArrayList<AttributeResult>();

        for (VariableElement field : fields) {

            AttributeResult attributeResult = new AttributeResult();
            attributeResult.setField(field);

            String getterMethodName = BeanUtils.getGetterMethodName(field);
            attributeResult.setGetterMethodName(BeanUtils.getGetterMethodName(field));
            attributeResult.setSetterMethodName(BeanUtils.getSetterMethodName(field));


            // just add those fields with both getters and setters
            if (attributeResult.hasGetter() && attributeResult.hasSetter()) {
                result.add(attributeResult);
            }
        }

        return result.toArray(new AttributeResult[result.size()]);

    }


    public static boolean checkHasGetter(VariableElement field) {

        if (field == null || field.getKind() != ElementKind.FIELD || CoreMatchers.BY_MODIFIER.getMatcher().checkForMatchingCharacteristic(field, Modifier.STATIC)) {
            return false;
        }

        TypeElement typeElement = (TypeElement) ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS);


        return checkLombokDataAnnotation(typeElement)
                || checkLombokGetterAnnotationOnType(typeElement)
                || checkLombokGetterAnnotationOnField(field)
                || checkHasGetterMethod(field, typeElement)
                ;

    }

    public static boolean checkHasSetter(VariableElement field) {

        if (field == null || field.getKind() != ElementKind.FIELD) {
            return false;
        }

        TypeElement typeElement = (TypeElement) ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS);


        return checkLombokDataAnnotation(typeElement)
                || checkLombokSetterAnnotationOnType(typeElement)
                || checkLombokSetterAnnotationOnField(field)
                || checkHasSetterMethod(field, typeElement)
                ;

    }

    public static boolean checkLombokDataAnnotation(TypeElement typeElement) {

        return AnnotationUtils.getAnnotationMirror(typeElement, "lombok.Data") != null;

    }

    public static boolean checkLombokGetterAnnotationOnType(TypeElement typeElement) {

        return AnnotationUtils.getAnnotationMirror(typeElement, "lombok.Getter") != null;

    }

    public static boolean checkLombokGetterAnnotationOnField(VariableElement variableElement) {

        return AnnotationUtils.getAnnotationMirror(variableElement, "lombok.Getter") != null;

    }


    /**
     * Get the getters method name.
     *
     * @param field
     * @return the getters method name or null if field has no getter
     */
    public static String getGetterMethodName(VariableElement field) {

        if (field == null || field.getKind() != ElementKind.FIELD) {
            return null;
        }

        TypeElement typeElement = (TypeElement) ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(field, ElementKind.CLASS);


        ExecutableElement getterMethod = getGetterMethod(field, typeElement);

        if (getterMethod != null) {
            return getterMethod.getSimpleName().toString();
        }

        if (checkLombokDataAnnotation(typeElement)
                || checkLombokGetterAnnotationOnType(typeElement)
                || checkLombokGetterAnnotationOnField(field)) {

            return TypeUtils.TypeComparison.isTypeEqual(field.asType(), TypeUtils.TypeRetrieval.getTypeMirror(boolean.class)) ? getPrefixedName("is", field.getSimpleName().toString()) : getPrefixedName("get", field.getSimpleName().toString());

        }

        return null;
    }

    public static String getSetterMethodName(VariableElement field) {
        if (field == null || field.getKind() != ElementKind.FIELD) {
            return null;
        }

        return checkHasSetter(field) ? getPrefixedName("set", field.getSimpleName().toString()) : null;
    }

    protected static boolean checkHasGetterMethod(VariableElement field, TypeElement typeElement) {

        return getGetterMethod(field, typeElement) != null;

    }

    protected static ExecutableElement getGetterMethod(VariableElement field, TypeElement typeElement) {
        List<ExecutableElement> result = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(CoreMatchers.IS_METHOD)
                .applyFilter(CoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC)
                .applyFilter(CoreMatchers.BY_MODIFIER).filterByNoneOf(Modifier.ABSTRACT, Modifier.STATIC)
                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf(getPossibleGetterOrSetterNames(field, GETTER_PREFIXES))
                .applyFilter(CoreMatchers.HAS_NO_PARAMETERS)
                .applyFilter(CoreMatchers.BY_RETURN_TYPE_MIRROR).filterByOneOf(field.asType())
                .getResult();

        return result.size() >= 1 ? result.get(0) : null;
    }

    protected static boolean checkHasSetterMethod(VariableElement field, TypeElement typeElement) {


        return getSetterMethod(field, typeElement) != null;


    }

    protected static ExecutableElement getSetterMethod(VariableElement field, TypeElement typeElement) {

        TypeMirror[] parameters = {field.asType()};

        List<ExecutableElement> result = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(CoreMatchers.IS_METHOD)
                .applyFilter(CoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC)
                .applyFilter(CoreMatchers.BY_MODIFIER).filterByNoneOf(Modifier.ABSTRACT, Modifier.STATIC)
                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf(getPossibleGetterOrSetterNames(field, SETTER_PREFIXES))
                .applyFilter(CoreMatchers.HAS_VOID_RETURN_TYPE)
                .applyFilter(CoreMatchers.BY_PARAMETER_TYPE_MIRROR).filterByOneOf(parameters)
                .getResult();

        return result.size() >= 1 ? result.get(0) : null;
    }

    protected static String[] getPossibleGetterOrSetterNames(VariableElement field, String[] prefixes) {
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
