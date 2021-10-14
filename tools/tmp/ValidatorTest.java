package io.toolisticon.aptk.tools.characteristicsvalidator;

import io.toolisticon.aptk.tools.matcher.impl.ParameterTypeFqnMatcher;
import io.toolisticon.aptk.tools.matcher.impl.ParameterTypeMatcher;
import io.toolisticon.aptk.tools.matcher.impl.RawTypeMatcher;
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

        MatcherAssert.assertThat(Validators.MODIFIER_VALIDATOR.getValidator().getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.MODIFIER_MATCHER.getMatcher()));

    }

    @Test
    public void testModifierValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Modifier>) Validators.InAndExclusiveElementValidators.getModifierValidator()).getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.MODIFIER_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- ANNOTATION_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testAnnotationValidator() {

        MatcherAssert.assertThat(Validators.ANNOTATION_VALIDATOR.getValidator().getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.ANNOTATION_MATCHER.getMatcher()));

    }

    @Test
    public void testAnnotationValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class<? extends Annotation>>) Validators.InAndExclusiveElementValidators.getAnnotationValidator()).getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.ANNOTATION_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testNameValidator() {

        MatcherAssert.assertThat(Validators.NAME_VALIDATOR.getValidator().getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.NAME_MATCHER.getMatcher()));

    }

    @Test
    public void testNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validators.InAndExclusiveElementValidators.getNameValidator()).getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.NAME_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- REGEX_NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testRegexNameValidator() {

        MatcherAssert.assertThat(Validators.REGEX_NAME_VALIDATOR.getValidator().getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.REGEX_NAME_MATCHER.getMatcher()));

    }

    @Test
    public void testRegexNameValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String>) Validators.InAndExclusiveElementValidators.getRegexNameValidator()).getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.REGEX_NAME_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- ELEMENT_KIND_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testElementKindValidator() {

        MatcherAssert.assertThat(Validators.ELEMENT_KIND_VALIDATOR.getValidator().getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.ELEMENT_KIND_MATCHER.getMatcher()));

    }

    @Test
    public void testElementKindValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<ElementKind>) Validators.InAndExclusiveElementValidators.getElementKindValidator()).getMatcher(), Matchers.is(io.toolisticon.aptk.tools.characteristicsmatcher.Matchers.ELEMENT_KIND_MATCHER.getMatcher()));

    }

    // ------------------------------------
    // -- PARAMETER_TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterValidator() {

        MatcherAssert.assertThat(Validators.PARAMETER_TYPE_VALIDATOR.getValidator().getMatcher(), Matchers.instanceOf(ParameterTypeMatcher.class));

    }

    @Test
    public void testParameterValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class[]>) Validators.InAndExclusiveElementValidators.getParameterValidator()).getMatcher(), Matchers.instanceOf(ParameterTypeMatcher.class));

    }

    // ------------------------------------
    // -- PARAMETER_TYPE_FQN_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterFQNValidator() {

        MatcherAssert.assertThat(Validators.PARAMETER_TYPE_FQN_VALIDATOR.getValidator().getMatcher(), Matchers.instanceOf(ParameterTypeFqnMatcher.class));

    }

    @Test
    public void testParameterFQNValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<String[]>) Validators.InAndExclusiveElementValidators.getParameterFqnValidator()).getMatcher(), Matchers.instanceOf(ParameterTypeFqnMatcher.class));

    }

    // ------------------------------------
    // -- RAW_TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testTypeValidator() {

        MatcherAssert.assertThat(Validators.RAW_TYPE_VALIDATOR.getValidator().getMatcher(), Matchers.instanceOf(RawTypeMatcher.class));

    }


    @Test
    public void testTypeValidator_GetValidatorByMethod() {

        MatcherAssert.assertThat(((GenericElementCharacteristicValidator<Class>) Validators.InAndExclusiveElementValidators.getRawTypeValidator()).getMatcher(), Matchers.instanceOf(RawTypeMatcher.class));

    }


}
