package io.toolisticon.aptk.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for {@link StringOperand}.
 */
public class StringOperandTest {

    @Test
    public void test_getOperandsJavaType() {

        MatcherAssert.assertThat(new StringOperand("'test'").getOperandsJavaType(), Matchers.equalTo((Class) String.class));

    }

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
    public void testNonMatchingStringPattern() {

        // This is more or less a theoretical case
        MatcherAssert.assertThat(new StringOperand("'ABC").value(), Matchers.nullValue());
        MatcherAssert.assertThat(new StringOperand("ABC'").value(), Matchers.nullValue());
        MatcherAssert.assertThat(new StringOperand("ABC").value(), Matchers.nullValue());

    }

    @Test
    @Ignore
    public void testStringWithEscapedEscapeChars() {
        MatcherAssert.assertThat(new StringOperand("'ABC\\\\ DEF'").value(), Matchers.is("ABC\\ DEF"));
        MatcherAssert.assertThat(new StringOperand("'ABC\\\\\\' DEF'").value(), Matchers.is("ABC\\' DEF"));
    }
}
