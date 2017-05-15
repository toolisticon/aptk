package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.AnnotationElementCharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.GenericElementCharacteristicValidator;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.KindElementcharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ModifierElementCharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.NameElementCharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.RegexNameElementCharacteristicMatcher;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Utility class for accessing validators.
 */
public class Validators {

    /**
     * Hidden constructor.
     */
    private Validators() {

    }

    public final static GenericElementCharacteristicValidator<Modifier> MODIFIER_VALIDATOR = new GenericElementCharacteristicValidator<Modifier>(new ModifierElementCharacteristicMatcher());
    public final static GenericElementCharacteristicValidator<String> NAME_VALIDATOR = new GenericElementCharacteristicValidator<String>(new NameElementCharacteristicMatcher());
    public final static GenericElementCharacteristicValidator<String> REGEX_NAME_VALIDATOR = new GenericElementCharacteristicValidator<String>(new RegexNameElementCharacteristicMatcher());
    public final static GenericElementCharacteristicValidator<Class<? extends Annotation>> ANNOTATION_VALIDATOR = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(new AnnotationElementCharacteristicMatcher());
    public final static GenericElementCharacteristicValidator<ElementKind> ELEMENT_KIND_VALIDATOR = new GenericElementCharacteristicValidator<ElementKind>(new KindElementcharacteristicMatcher());


    /**
     * Convenience method for getting and using a Modifier matching validator.
     *
     * @return the validator instance
     */
    public static InclusiveElementValidator<Modifier> getModifierValidator() {
        return MODIFIER_VALIDATOR;
    }

    /**
     * Convenience method for getting and using a name matching validator.
     *
     * @return the validator instance
     */
    public static ExclusiveElementValidator<String> getNameValidator() {
        return NAME_VALIDATOR;
    }

    /**
     * Convenience method for getting and using a name matching validator by regular expressions.
     *
     * @return the validator instance
     */
    public static InclusiveElementValidator<String> getRegexNameValidator() {
        return REGEX_NAME_VALIDATOR;
    }

    /**
     * Convenience method for getting and using an annotation matching validator.
     *
     * @return the validator instance
     */
    public static InclusiveElementValidator<Class<? extends Annotation>> getAnnotationValidator() {
        return ANNOTATION_VALIDATOR;
    }

    /**
     * Convenience method for getting and using a ElementKind matching validator.
     *
     * @return the validator instance
     */
    public static ExclusiveElementValidator<ElementKind> getElementKindValidator() {
        return ELEMENT_KIND_VALIDATOR;
    }

}
