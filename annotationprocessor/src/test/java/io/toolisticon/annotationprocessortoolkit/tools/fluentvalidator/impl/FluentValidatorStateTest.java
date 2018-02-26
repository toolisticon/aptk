package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.impl;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link FluentValidatorState}
 */
public class FluentValidatorStateTest {


    @Test
    public void testSetFailingResult() {

        FluentValidatorState unit = new FluentValidatorState();

        MatcherAssert.assertThat(unit.getValidationResult(), Matchers.is(true));

        unit.setAsFailedValidation();
        MatcherAssert.assertThat(unit.getValidationResult(), Matchers.is(false));

    }

    @Test
    public void testAddAndIssueMessages() {
        FluentValidatorMessage fluentValidatorMessage1 = Mockito.mock(FluentValidatorMessage.class);
        FluentValidatorMessage fluentValidatorMessage2 = Mockito.mock(FluentValidatorMessage.class);

        FluentValidatorState unit = new FluentValidatorState();
        unit.addMessage(fluentValidatorMessage1);
        unit.addMessage(fluentValidatorMessage2);

        unit.issueMessages();
        Mockito.verify(fluentValidatorMessage1, Mockito.times(1)).issueMessage();
        Mockito.verify(fluentValidatorMessage2, Mockito.times(1)).issueMessage();
    }


}
