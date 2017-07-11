package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ParameterExecutableMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ParameterFQNExecutableMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.RawTypeMatcher;
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

        MatcherAssert.assertThat(Validators.getModifierValidator().getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getModifierMatcher().getMatcher()));

    }

    @Test
    public void testModifierValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Modifier>) Validators.InAndExclusiveElementValidators.getModifierValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getModifierMatcher().getMatcher()));

    }

    // ------------------------------------
    // -- ANNOTATION_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testAnnotationValidator() {

        MatcherAssert.assertThat(Validators.getAnnotationValidator().getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getAnnotationMatcher().getMatcher()));

    }

    @Test
    public void testAnnotationValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class<? extends Annotation>>) Validators.InAndExclusiveElementValidators.getAnnotationValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getAnnotationMatcher().getMatcher()));

    }

    // ------------------------------------
    // -- NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testNameValidator() {

        MatcherAssert.assertThat(Validators.getNameValidator().getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getNameMatcher().getMatcher()));

    }

    @Test
    public void testNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validators.InAndExclusiveElementValidators.getNameValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getNameMatcher().getMatcher()));

    }

    // ------------------------------------
    // -- REGEX_NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testRegexNameValidator() {

        MatcherAssert.assertThat(Validators.getRegexNameValidator().getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getRegexNameMatcher().getMatcher()));

    }

    @Test
    public void testRegexNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validators.InAndExclusiveElementValidators.getRegexNameValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getRegexNameMatcher().getMatcher()));

    }

    // ------------------------------------
    // -- ELEMENT_KIND_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testElementKindValidator() {

        MatcherAssert.assertThat(Validators.getElementKindValidator().getValidator().getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getElementKindMatcher().getMatcher()));

    }

    @Test
    public void testElementKindValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<ElementKind>) Validators.InAndExclusiveElementValidators.getElementKindValidator()).getMatcher(), Matchers.is(de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers.getElementKindMatcher().getMatcher()));

    }

    // ------------------------------------
    // -- PARAMETER_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterValidator() {

        MatcherAssert.assertThat(Validators.getParameterValidator(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterExecutableMatcher.class));

    }

    @Test
    public void testParameterValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class[]>) Validators.InAndExclusiveElementValidators.getParameterValidator(null)).getMatcher(), Matchers.instanceOf(ParameterExecutableMatcher.class));

    }

    // ------------------------------------
    // -- PARAMETER_FQN_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterFQNValidator() {

        MatcherAssert.assertThat(Validators.getParameterFqnValidator(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterFQNExecutableMatcher.class));

    }

    @Test
    public void testParameterFQNValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String[]>) Validators.InAndExclusiveElementValidators.getParameterFqnValidator(null)).getMatcher(), Matchers.instanceOf(ParameterFQNExecutableMatcher.class));

    }

    // ------------------------------------
    // -- RAW_TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testTypeValidator() {

        MatcherAssert.assertThat(Validators.getRawTypeValidator(null).getValidator().getMatcher(), Matchers.instanceOf(RawTypeMatcher.class));

    }


    @Test
    public void testTypeValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class>) Validators.InAndExclusiveElementValidators.getRawTypeValidator(null)).getMatcher(), Matchers.instanceOf(RawTypeMatcher.class));

    }


}
