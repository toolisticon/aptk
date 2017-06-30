package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ParameterExecutableElementCharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ParameterFQNExecutableElementCharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.TypeElementCharacteristicMatcher;
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

        MatcherAssert.assertThat(Validator.MODIFIER_VALIDATOR.getValidator().getMatcher(), Matchers.is(Matcher.MODIFIER_MATCHER.getMatcher()));

    }

    @Test
    public void testModifierValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator()).getMatcher(), Matchers.is(Matcher.MODIFIER_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- ANNOTATION_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testAnnotationValidator() {

        MatcherAssert.assertThat(Validator.ANNOTATION_VALIDATOR.getValidator().getMatcher(), Matchers.is(Matcher.ANNOTATION_MATCHER.getMatcher()));

    }

    @Test
    public void testAnnotationValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class<? extends Annotation>>) Validator.getAnnotationValidator()).getMatcher(), Matchers.is(Matcher.ANNOTATION_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testNameValidator() {

        MatcherAssert.assertThat(Validator.NAME_VALIDATOR.getValidator().getMatcher(), Matchers.is(Matcher.NAME_MATCHER.getMatcher()));

    }

    @Test
    public void testNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validator.getNameValidator()).getMatcher(), Matchers.is(Matcher.NAME_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- REGEX_NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testRegexNameValidator() {

        MatcherAssert.assertThat(Validator.REGEX_NAME_VALIDATOR.getValidator().getMatcher(), Matchers.is(Matcher.REGEX_NAME_MATCHER.getMatcher()));

    }

    @Test
    public void testRegexNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validator.getRegexNameValidator()).getMatcher(), Matchers.is(Matcher.REGEX_NAME_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- ELEMENT_KIND_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testElementKindValidator() {

        MatcherAssert.assertThat(Validator.ELEMENT_KIND_VALIDATOR.getValidator().getMatcher(), Matchers.is(Matcher.ELEMENT_KIND_MATCHER.getMatcher()));

    }

    @Test
    public void testElementKindValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<ElementKind>) Validator.getElementKindValidator()).getMatcher(), Matchers.is(Matcher.ELEMENT_KIND_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- PARAMETER_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterValidator() {

        MatcherAssert.assertThat(Validator.PARAMETER_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterExecutableElementCharacteristicMatcher.class));

    }

    @Test
    public void testParameterValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(Validator.PARAMETER_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterExecutableElementCharacteristicMatcher.class));
    }

    // ------------------------------------
    // -- PARAMETER_FQN_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterFQNValidator() {

        MatcherAssert.assertThat(Validator.PARAMETER_FQN_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterFQNExecutableElementCharacteristicMatcher.class));

    }

    @Test
    public void testParameterFQNValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(Validator.PARAMETER_FQN_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(ParameterFQNExecutableElementCharacteristicMatcher.class));
    }

    // ------------------------------------
    // -- TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testTypeValidator() {

        MatcherAssert.assertThat(Validator.TYPE_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(TypeElementCharacteristicMatcher.class));

    }


    @Test
    public void testTypeValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(Validator.TYPE_VALIDATOR(null).getValidator().getMatcher(), Matchers.instanceOf(TypeElementCharacteristicMatcher.class));
    }


}
