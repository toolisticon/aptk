package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ParameterExecutableMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ParameterFQNExecutableMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.TypeMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * unit test for {@link Validator} class.
 */
public class ValidatorTest {


    // ------------------------------------
    // -- MODIFIER_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testModifierValidator() {

        MatcherAssert.assertThat(Validators.MODIFIER_VALIDATOR.getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.MODIFIER_MATCHER.getMatcher()));

    }

    @Test
    public void testModifierValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Modifier>) Validators.getModifierValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.MODIFIER_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- ANNOTATION_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testAnnotationValidator() {

        MatcherAssert.assertThat(Validators.ANNOTATION_VALIDATOR.getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.ANNOTATION_MATCHER.getMatcher()));

    }

    @Test
    public void testAnnotationValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class<? extends Annotation>>) Validators.getAnnotationValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.ANNOTATION_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testNameValidator() {

        MatcherAssert.assertThat(Validators.NAME_VALIDATOR.getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.NAME_MATCHER.getMatcher()));

    }

    @Test
    public void testNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validators.getNameValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.NAME_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- REGEX_NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testRegexNameValidator() {

        MatcherAssert.assertThat(Validators.REGEX_NAME_VALIDATOR.getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.REGEX_NAME_MATCHER.getMatcher()));

    }

    @Test
    public void testRegexNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validators.getRegexNameValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.REGEX_NAME_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- ELEMENT_KIND_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testElementKindValidator() {

        MatcherAssert.assertThat(Validators.ELEMENT_KIND_VALIDATOR.getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.ELEMENT_KIND_MATCHER.getMatcher()));

    }

    @Test
    public void testElementKindValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<ElementKind>) Validators.getElementKindValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.ELEMENT_KIND_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- PARAMETER_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterValidator() {

        MatcherAssert.assertThat(Validators.PARAMETER_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterExecutableMatcher.class));

    }

    @Test
    public void testParameterValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class[]>) Validators.getParameterValidator(null)).getMatcher(), Matchers.instanceOf(ParameterExecutableMatcher.class));

    }

    // ------------------------------------
    // -- PARAMETER_FQN_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterFQNValidator() {

        MatcherAssert.assertThat(Validators.PARAMETER_FQN_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterFQNExecutableMatcher.class));

    }

    @Test
    public void testParameterFQNValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String[]>) Validators.getParameterFqnValidator(null)).getMatcher(), Matchers.instanceOf(ParameterFQNExecutableMatcher.class));

    }

    // ------------------------------------
    // -- TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testTypeValidator() {

        MatcherAssert.assertThat(Validators.TYPE_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(TypeMatcher.class));

    }


    @Test
    public void testTypeValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class>) Validators.getTypeValidator(null)).getMatcher(), Matchers.instanceOf(TypeMatcher.class));

    }


}
