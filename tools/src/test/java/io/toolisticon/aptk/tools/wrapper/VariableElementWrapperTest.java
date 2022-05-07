package io.toolisticon.aptk.tools.wrapper;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Unit Test for {@link VariableElementWrapper}.
 */
public class VariableElementWrapperTest {

    @Test
    public void test_wrap_and_unwrap() {
         VariableElement ve = Mockito.mock(VariableElement.class);

        MatcherAssert.assertThat(ElementWrapper.wrap(ve).unwrap(), Matchers.is(ve));
    }

    @Test
    public void test_getConstantValue() {
        VariableElement ve = Mockito.spy(VariableElement.class);
        VariableElementWrapper.wrap(ve).getConstantValue();
        Mockito.verify(ve, Mockito.times(1)).getConstantValue();
    }



}
