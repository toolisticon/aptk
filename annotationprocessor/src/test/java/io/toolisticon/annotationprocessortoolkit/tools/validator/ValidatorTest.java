package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * unit test for {@link Validators} class.
 */
public class ValidatorTest {


    // ------------------------------------
    // -- MODIFIER_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testModifierValidator() {

        MatcherAssert.assertThat(Validators.MODIFIER_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_MODIFIER.getMatcher()));

    }


    // ------------------------------------
    // -- ANNOTATION_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testAnnotationValidator() {

        MatcherAssert.assertThat(Validators.ANNOTATION_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_ANNOTATION.getMatcher()));

    }


    // ------------------------------------
    // -- NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testNameValidator() {

        MatcherAssert.assertThat(Validators.NAME_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_NAME.getMatcher()));

    }


    // ------------------------------------
    // -- REGEX_NAME_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testRegexNameValidator() {

        MatcherAssert.assertThat(Validators.REGEX_NAME_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_REGEX_NAME.getMatcher()));

    }


    // ------------------------------------
    // -- ELEMENT_KIND_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testElementKindValidator() {

        MatcherAssert.assertThat(Validators.ELEMENT_KIND_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_ELEMENT_KIND.getMatcher()));

    }


    // ------------------------------------
    // -- PARAMETER_TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterValidator() {

        MatcherAssert.assertThat(Validators.PARAMETER_TYPE_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_PARAMETER_TYPE.getMatcher()));

    }


    // ------------------------------------
    // -- PARAMETER_TYPE_FQN_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testParameterFQNValidator() {

        MatcherAssert.assertThat(Validators.PARAMETER_TYPE_FQN_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher()));

    }


    // ------------------------------------
    // -- RAW_TYPE_VALIDATOR Validator tests
    // ------------------------------------

    @Test
    public void testTypeValidator() {

        MatcherAssert.assertThat(Validators.RAW_TYPE_VALIDATOR.getMatcher(), Matchers.is(CoreMatchers.BY_RAW_TYPE.getMatcher()));

    }


}
