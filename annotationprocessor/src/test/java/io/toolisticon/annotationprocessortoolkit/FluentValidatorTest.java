package io.toolisticon.annotationprocessortoolkit;

import io.toolisticon.annotationprocessortoolkit.validators.AbstractFluentValidator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Test class for {@link AbstractFluentValidator}.
 */
public class FluentValidatorTest {

    private static class TestFluentValidator<T extends Element> extends AbstractFluentValidator<TestFluentValidator<T>, T> {
        public TestFluentValidator(T elementToValidate) {

            super(elementToValidate);

        }

        private TestFluentValidator(TestFluentValidator<T> previousFluentModifierElementValidator, boolean currentResult) {

            super(previousFluentModifierElementValidator, currentResult);

        }


        @Override
        protected TestFluentValidator createNextFluentValidator(boolean nextResult) {
            return new TestFluentValidator(this, nextResult);
        }


        public Diagnostic.Kind getMessageLevel() {
            return super.getMessageLevel();
        }

        public boolean isErrorLevel() {
            return super.isErrorLevel();
        }

        public TestFluentValidator<T> doValidation(boolean outcome) {

            if (!outcome) {
                getMessagerUtils().printMessage(getElement(), getMessageLevel(), getCustomOrDefaultMessage("XXX: ${0}", "A"));
            }

            return createNextFluentValidator(outcome);
        }

        public TestFluentValidator<T> doValidation() {
            return createNextFluentValidator(getValidationResult());
        }
    }

    ProcessingEnvironment processingEnvironment;
    Messager messager;

    Element element;

    TestFluentValidator<Element> unit;


    @Before
    public void init() {


        messager = Mockito.spy(Messager.class);
        element = Mockito.mock(Element.class);

        processingEnvironment = Mockito.mock(ProcessingEnvironment.class);
        Mockito.when(processingEnvironment.getMessager()).thenReturn(messager);

        ToolingProvider.setTooling(processingEnvironment);


        unit = new TestFluentValidator<Element>(element);
    }

    @Test
    public void testOfSettingMessageLevel() {

        // check if message level is set and if same TestFluentValidator is returned

        // info
        TestFluentValidator<Element> nextFluentValidator = unit.info();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.NOTE));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));

        // warning
        nextFluentValidator = nextFluentValidator.warning();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.WARNING));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));

        // mandatory warning
        nextFluentValidator = nextFluentValidator.mandatoryWarning();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.MANDATORY_WARNING));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));

        // other
        nextFluentValidator = nextFluentValidator.other();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.OTHER));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));

        // error
        nextFluentValidator = nextFluentValidator.error();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.ERROR));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));

    }

    @Test
    public void testIfErrorLevelIsDetectedCorrectly() {

        // check if message level is set and if same TestFluentValidator is returned

        // info
        TestFluentValidator<Element> nextFluentValidator = unit.info();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.NOTE));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));
        MatcherAssert.assertThat(nextFluentValidator.isErrorLevel(), Matchers.equalTo(false));

        // warning
        nextFluentValidator = nextFluentValidator.warning();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.WARNING));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));
        MatcherAssert.assertThat(nextFluentValidator.isErrorLevel(), Matchers.equalTo(false));


        // mandatory warning
        nextFluentValidator = nextFluentValidator.mandatoryWarning();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.MANDATORY_WARNING));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));
        MatcherAssert.assertThat(nextFluentValidator.isErrorLevel(), Matchers.equalTo(false));


        // other
        nextFluentValidator = nextFluentValidator.other();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.OTHER));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));
        MatcherAssert.assertThat(nextFluentValidator.isErrorLevel(), Matchers.equalTo(false));


        // error
        nextFluentValidator = nextFluentValidator.error();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.ERROR));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.equalTo(unit));
        MatcherAssert.assertThat(nextFluentValidator.isErrorLevel(), Matchers.equalTo(true));


    }

    @Test
    public void testIfMessageLevelIsTransportedThroughValidations() {

        TestFluentValidator<Element> nextFluentValidator = unit.info().doValidation().doValidation();
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.NOTE));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.not(Matchers.equalTo(unit)));

        // warning
        nextFluentValidator = nextFluentValidator.warning().doValidation().doValidation();
        ;
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.WARNING));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.not(Matchers.equalTo(unit)));


        // mandatory warning
        nextFluentValidator = nextFluentValidator.mandatoryWarning().doValidation().doValidation();
        ;
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.MANDATORY_WARNING));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.not(Matchers.equalTo(unit)));


        // other
        nextFluentValidator = nextFluentValidator.other().doValidation().doValidation();
        ;
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.OTHER));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.not(Matchers.equalTo(unit)));


        // error
        nextFluentValidator = nextFluentValidator.error().doValidation().doValidation();
        ;
        MatcherAssert.assertThat(nextFluentValidator.getMessageLevel(), Matchers.is(Diagnostic.Kind.ERROR));
        MatcherAssert.assertThat(nextFluentValidator, Matchers.not(Matchers.equalTo(unit)));

    }

    @Test
    public void testCustomMessage() {

        unit.setCustomMessage("YYY : ${0}", "B").doValidation(false);
        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, "YYY : B", element);


    }

    @Test
    public void testCustomMessageWithPreviouslySetMessageLevel() {

        unit.info().setCustomMessage("YYY : ${0}", "B").doValidation(false);
        Mockito.verify(messager).printMessage(Diagnostic.Kind.NOTE, "YYY : B", element);


    }

    @Test
    public void testCustomMessageWithCustomMessageLevel() {

        unit.info().setCustomMessage(Diagnostic.Kind.ERROR, "YYY : ${0}", "B").doValidation(false);
        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, "YYY : B", element);

    }

    @Test
    public void testIfCustomMessageWithCustomMessageLevelDoesntAffectFollowingValidations() {

        unit.info().setCustomMessage(Diagnostic.Kind.ERROR, "YYY : ${0}", "B").doValidation(true).doValidation(false);
        Mockito.verify(messager).printMessage(Diagnostic.Kind.NOTE, "XXX: A", element);

    }
}
