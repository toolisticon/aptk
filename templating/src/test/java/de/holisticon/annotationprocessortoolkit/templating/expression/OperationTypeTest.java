package de.holisticon.annotationprocessortoolkit.templating.expression;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.OperandFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for {@link de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType}.
 */
public class OperationTypeTest {

    // --------------------------------------------------------
    // -- AND
    // --------------------------------------------------------


    @Test
    public void and_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.AND.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void and_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.AND.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void and_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.AND.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void and_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.AND.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        OperationType.AND.doOperation(operand1, operand2).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        OperationType.AND.doOperation(operand1, operand2).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        OperationType.AND.doOperation(operand1, operand2).value();

    }


    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.AND.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.AND.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.AND.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- OR
    // --------------------------------------------------------


    @Test
    public void or_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.OR.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void or_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.OR.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void or_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.OR.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void or_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.OR.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        OperationType.OR.doOperation(operand1, operand2).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        OperationType.OR.doOperation(operand1, operand2).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        OperationType.OR.doOperation(operand1, operand2).value();

    }


    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.OR.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.OR.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.OR.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- EQUAL
    // --------------------------------------------------------


    @Test
    public void equal_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));


    }

    @Test
    public void equal_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));


    }

    @Test
    public void equal_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MismatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MismatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MismatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        OperationType.EQUAL.doOperation(operand1, operand2).value();

    }


    @Test
    public void equal_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        MatcherAssert.assertThat((Boolean) OperationType.EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));


    }


    @Test(expected = IllegalArgumentException.class)
    public void equal_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.EQUAL.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void equal_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.EQUAL.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void equal_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.EQUAL.doOperation(null, null).value();

    }


    // --------------------------------------------------------
    // -- NOT EQUAL
    // --------------------------------------------------------


    @Test
    public void notEqual_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));


    }

    @Test
    public void notEqual_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));


    }

    @Test
    public void notEqual_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MismatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MismatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MismatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test(expected = IllegalArgumentException.class)
    public void notEqual_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        OperationType.NOT_EQUAL.doOperation(operand1, operand2).value();

    }


    @Test
    public void notEqual_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        MatcherAssert.assertThat((Boolean) OperationType.NOT_EQUAL.doOperation(operand1, operand2).value(), Matchers.is(false));


    }


    @Test(expected = IllegalArgumentException.class)
    public void notEqual_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.NOT_EQUAL.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void notEqual_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.NOT_EQUAL.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void notEqual_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.NOT_EQUAL.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- LESS_OR_EQUAL
    // --------------------------------------------------------


    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingBiggerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingSmallerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingSmallerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingBiggerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 6.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }


    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.LESS_OR_EQUAL_THAN.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- LESS_OR_EQUAL
    // --------------------------------------------------------


    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingBiggerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingSmallerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingSmallerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingBiggerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 6.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }


    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- LESS THAN
    // --------------------------------------------------------


    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.LESS_THAN.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        OperationType.LESS_THAN.doOperation(operand1, operand2);

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingBiggerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingSmallerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingSmallerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingBiggerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 6.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.LESS_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }


    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.LESS_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.LESS_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.LESS_THAN.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.LESS_THAN.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.LESS_THAN.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.LESS_THAN.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- GREATER_THAN
    // --------------------------------------------------------


    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.GREATER_THAN.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(String.class, "test");
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        OperationType.GREATER_THAN.doOperation(operand1, operand2);

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MismatchingBiggerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_THAN.doOperation(operand1, operand2).value(), Matchers.is(false));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Boolean) OperationType.GREATER_THAN.doOperation(operand1, operand2).value(), Matchers.is(true));

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.GREATER_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.GREATER_THAN.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.GREATER_THAN.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.GREATER_THAN.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.GREATER_THAN.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.GREATER_THAN.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- MULTIPLY
    // --------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.MULTIPLICATION.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(String.class, "test");
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        OperationType.MULTIPLICATION.doOperation(operand1, operand2);

    }

    @Test
    public void multiplication_doOperation_withValidOperands_TwoLongOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Long) OperationType.MULTIPLICATION.doOperation(operand1, operand2).value(), Matchers.is(30L));

    }

    @Test
    public void multiplication_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Long) OperationType.MULTIPLICATION.doOperation(operand1, operand2).value(), Matchers.is(30L));

    }

    @Test
    public void multiplication_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 6f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Double) OperationType.MULTIPLICATION.doOperation(operand1, operand2).value(), Matchers.is(30.0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.MULTIPLICATION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.MULTIPLICATION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.MULTIPLICATION.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.MULTIPLICATION.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.MULTIPLICATION.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.MULTIPLICATION.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- DIVIDE
    // --------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.DIVISION.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(String.class, "test");
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        OperationType.DIVISION.doOperation(operand1, operand2);

    }

    @Test
    public void division_doOperation_withValidOperands_TwoLongOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Long) OperationType.DIVISION.doOperation(operand1, operand2).value(), Matchers.is(6L));

    }

    @Test
    public void division_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Long) OperationType.DIVISION.doOperation(operand1, operand2).value(), Matchers.is(6L));

    }

    @Test
    public void division_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 30.0f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Double) OperationType.DIVISION.doOperation(operand1, operand2).value(), Matchers.is(6.0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.DIVISION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.DIVISION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.DIVISION.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.DIVISION.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.DIVISION.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.DIVISION.doOperation(null, null).value();

    }

    // --------------------------------------------------------
    // -- ADD
    // --------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.ADDITION.doOperation(operand1, operand2);

    }


    @Test
    public void addition_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(String.class, "test");
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((String) OperationType.ADDITION.doOperation(operand1, operand2).value(), Matchers.is("test5"));


    }

    @Test
    public void addition_doOperation_withValidOperands_TwoLongOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Long) OperationType.ADDITION.doOperation(operand1, operand2).value(), Matchers.is(35L));

    }

    @Test
    public void addition_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Long) OperationType.ADDITION.doOperation(operand1, operand2).value(), Matchers.is(35L));

    }

    @Test
    public void addition_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 30.0f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Double) OperationType.ADDITION.doOperation(operand1, operand2).value(), Matchers.is(35.0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.ADDITION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.ADDITION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.ADDITION.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.ADDITION.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.ADDITION.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.ADDITION.doOperation(null, null).value();

    }
    // --------------------------------------------------------
    // -- SUBTRACT
    // --------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withInvalidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        OperationType.SUBTRACTION.doOperation(operand1, operand2);

    }


    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withInvalidOperands_StringAndLong_Test() {

        Operand operand1 = OperandFactory.createOperationResult(String.class, "test");
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        OperationType.SUBTRACTION.doOperation(operand1, operand2);


    }

    @Test
    public void subtraction_doOperation_withValidOperands_TwoLongOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        MatcherAssert.assertThat((Long) OperationType.SUBTRACTION.doOperation(operand1, operand2).value(), Matchers.is(25L));

    }

    @Test
    public void subtraction_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Long) OperationType.SUBTRACTION.doOperation(operand1, operand2).value(), Matchers.is(25L));

    }

    @Test
    public void subtraction_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 30.0f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        MatcherAssert.assertThat((Double) OperationType.SUBTRACTION.doOperation(operand1, operand2).value(), Matchers.is(25.0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.SUBTRACTION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.SUBTRACTION.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, null);

        OperationType.SUBTRACTION.doOperation(operand1, operand2);


    }


    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withValidOperands_FirstOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.SUBTRACTION.doOperation(null, operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withValidOperands_secondOperandIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Integer.class, 5);

        OperationType.SUBTRACTION.doOperation(operand1, null).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withValidOperands_bothOperandsAreNull_Test() {

        OperationType.SUBTRACTION.doOperation(null, null).value();

    }

}
