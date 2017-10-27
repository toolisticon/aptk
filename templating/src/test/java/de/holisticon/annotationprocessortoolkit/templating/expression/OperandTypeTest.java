package de.holisticon.annotationprocessortoolkit.templating.expression;

import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.regex.Matcher;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType}.
 */
public class OperandTypeTest {

    @Test
    public void testString_pattern_reluctantlessness() {

        String testString = "ABC 'DEF' GHIJ 'KLM' XYZ";
        Matcher matcher = OperandType.STRING.getOperandPattern().matcher(testString);


        MatcherAssert.assertThat(matcher.find(), Matchers.is(true));
        MatcherAssert.assertThat(matcher.groupCount(), Matchers.is(1));
        MatcherAssert.assertThat(matcher.group(1), Matchers.is("DEF"));


    }

    @Test
    public void testString_pattern_escapeSingleQuoteCorrectly() {

        String testString = "ABC 'DEF\\' GHIJ \\'KLM' XYZ";
        Matcher matcher = OperandType.STRING.getOperandPattern().matcher(testString);


        MatcherAssert.assertThat(matcher.find(), Matchers.is(true));
        MatcherAssert.assertThat(matcher.groupCount(), Matchers.is(1));
        MatcherAssert.assertThat(matcher.group(1), Matchers.is("DEF\\' GHIJ \\'KLM"));


    }

    @Test
    public void testString_pattern_escapeSingleQuoteCorrectlyWithMultipleEscapesAtEnd() {

        String testString = "ABC 'DEF\\\\\\\\\\\\\\' GHIJ \\\\\\'KLM' XYZ";
        Matcher matcher = OperandType.STRING.getOperandPattern().matcher(testString);


        MatcherAssert.assertThat(matcher.find(), Matchers.is(true));
        MatcherAssert.assertThat(matcher.groupCount(), Matchers.is(1));
        MatcherAssert.assertThat(matcher.group(1), Matchers.is("DEF\\\\\\\\\\\\\\' GHIJ \\\\\\'KLM"));


    }


}
