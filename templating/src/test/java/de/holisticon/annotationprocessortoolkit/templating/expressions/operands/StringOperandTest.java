package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.templating.expressions.operands.StringOperand}.
 */
public class StringOperandTest {

    @Test
    public void testStringWithoutEscapes() {

        MatcherAssert.assertThat(new StringOperand("'ABC DEF'").value(), Matchers.is("ABC DEF"));
        MatcherAssert.assertThat(new StringOperand("'//%&§/'").value(), Matchers.is("//%&§/"));
        MatcherAssert.assertThat(new StringOperand("'A%§GS34234|||F'").value(), Matchers.is("A%§GS34234|||F"));

    }


    @Test
    public void testStringWithEscapedQuotes() {

        MatcherAssert.assertThat(new StringOperand("'ABC\\' DEF'").value(), Matchers.is("ABC' DEF"));
        MatcherAssert.assertThat(new StringOperand("'//%\\'&§\\'/'").value(), Matchers.is("//%'&§'/"));


    }

    @Test
    @Ignore
    public void testStringWithEscapedEscapeChars() {
        MatcherAssert.assertThat(new StringOperand("'ABC\\\\ DEF'").value(), Matchers.is("ABC\\ DEF"));
        MatcherAssert.assertThat(new StringOperand("'ABC\\\\\\' DEF'").value(), Matchers.is("ABC\\' DEF"));
    }
}
