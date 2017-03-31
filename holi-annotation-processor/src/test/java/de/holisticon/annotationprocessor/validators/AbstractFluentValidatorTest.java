package de.holisticon.annotationprocessor.validators;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Test class for {@link AbstractFluentValidator}.
 */
public class AbstractFluentValidatorTest {

    public static class UnitBaseClass extends AbstractFluentValidator<UnitBaseClass, Element> {

        UnitBaseClass(FrameworkToolWrapper ftw) {
            super(ftw, null);
        }

        @Override
        protected UnitBaseClass createNextFluentValidator(boolean nextResult) {
            return null;
        }

        public String getMessage(String defaultMessage, Object... messageParameters) {
            return this.getCustomOrDefaultMessage(defaultMessage, messageParameters);
        }

    }


    @Test
    public void testCustomMessageCreation() {

        ProcessingEnvironment processingEnvironmentMock = Mockito.mock(ProcessingEnvironment.class);

        UnitBaseClass unit = new UnitBaseClass(new FrameworkToolWrapper(processingEnvironmentMock));

        MatcherAssert.assertThat(unit.setCustomMessage("ABC ${0} DEF ${1} HIJ ${0}").getMessage(null), Matchers.is("ABC ${0} DEF ${1} HIJ ${0}"));
        MatcherAssert.assertThat(unit.setCustomMessage("ABC ${0} DEF ${1} HIJ ${0}", "0").getMessage(null), Matchers.is("ABC 0 DEF ${1} HIJ 0"));
        MatcherAssert.assertThat(unit.setCustomMessage("ABC ${0} DEF ${1} HIJ ${0}", "0", "1").getMessage(null), Matchers.is("ABC 0 DEF 1 HIJ 0"));
        MatcherAssert.assertThat(unit.setCustomMessage("ABC ${0} DEF ${1} HIJ ${0}", "0", "1", "XXX").getMessage(null), Matchers.is("ABC 0 DEF 1 HIJ 0"));


    }

}
