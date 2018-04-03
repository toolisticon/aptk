package io.toolisticon.annotationprocessortoolkit.templating.expressions.operations;

import io.toolisticon.annotationprocessortoolkit.templating.expressions.operands.NullValueOperand;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.operands.Operand;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.operands.OperandFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link OperationType}.
 */
public class OperationTypeTest {

    // --------------------------------------------------------
    // -- AND
    // --------------------------------------------------------


    @Test
    public void and_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.AND.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));


    }

    @Test
    public void and_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.AND.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void and_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.AND.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void and_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.AND.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        OperationType.AND.doOperation(operand1, operand2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        OperationType.AND.doOperation(operand1, operand2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        OperationType.AND.doOperation(operand1, operand2);

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

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.AND.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.AND.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void and_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.AND.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

    }


    // --------------------------------------------------------
    // -- OR
    // --------------------------------------------------------


    @Test
    public void or_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.OR.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void or_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.OR.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void or_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.OR.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void or_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.OR.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

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


    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.OR.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.OR.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void or_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.OR.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

    }

    // --------------------------------------------------------
    // -- EQUAL
    // --------------------------------------------------------


    @Test
    public void equal_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));


    }

    @Test
    public void equal_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));


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

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MismatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void equal_doOperation_withValidOperands_MismatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        Operand result = OperationType.EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

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


    @Test
    public void equal_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.EQUAL.doOperation(operand1, new NullValueOperand("null"));
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.EQUAL.doOperation(new NullValueOperand("null"), operand1);
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void equal_doOperation_withTwoNullValuedOperand_Test() {

        Operand result = OperationType.EQUAL.doOperation(new NullValueOperand("null"), new NullValueOperand("null"));
        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    // --------------------------------------------------------
    // -- NOT EQUAL
    // --------------------------------------------------------


    @Test
    public void notEqual_doOperation_withValidOperands_trueAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_trueAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));


    }

    @Test
    public void notEqual_doOperation_withValidOperands_FalseAndTrue_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));


    }

    @Test
    public void notEqual_doOperation_withValidOperands_FalseAndFalse_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, false);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MismatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MismatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_MismatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));


    }


    @Test
    public void notEqual_doOperation_withValidOperands_FirstOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, false);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_secondOperandValueIsNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withValidOperands_bothOperandsValuesAreNull_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, null);
        Operand operand2 = OperandFactory.createOperationResult(Boolean.class, null);

        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));


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

    @Test
    public void notEqual_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);


        Operand result = OperationType.NOT_EQUAL.doOperation(operand1, new NullValueOperand("null"));

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);

        Operand result = OperationType.NOT_EQUAL.doOperation(new NullValueOperand("null"), operand1);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void notEqual_doOperation_withTwoNullValuedOperand_Test() {

        Operand result = OperationType.NOT_EQUAL.doOperation(new NullValueOperand("null"), new NullValueOperand("null"));

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

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

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingSmallerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingSmallerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_MismatchingBiggerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 6.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessOrEqual_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

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

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.LESS_OR_EQUAL_THAN.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.LESS_OR_EQUAL_THAN.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessOrEqual_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.LESS_OR_EQUAL_THAN.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingSmallerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void greaterOrEqual_doOperation_withValidOperands_MismatchingSmallerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        Operand result = OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

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

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.GREATER_OR_EQUAL_THAN.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.GREATER_OR_EQUAL_THAN.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterOrEqual_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.GREATER_OR_EQUAL_THAN.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingSmallerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 6);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MatchingDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingSmallerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 6.0f);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_MismatchingBiggerDoubleAndFloatValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 6.0);
        Operand operand2 = OperandFactory.createOperationResult(Float.class, 5.0f);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void lessThan_doOperation_withValidOperands_DoubleAndLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Double.class, 5.0);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.LESS_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

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


    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.LESS_THAN.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.LESS_THAN.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThan_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.LESS_THAN.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.GREATER_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MismatchingSmallerLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 6L);

        Operand result = OperationType.GREATER_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MatchingLongValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.GREATER_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MatchingLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 5L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.GREATER_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(false));

    }

    @Test
    public void greaterThan_doOperation_withValidOperands_MismatchingBiggerLongAndIntegerValues_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.GREATER_THAN.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));
        MatcherAssert.assertThat((Boolean) result.value(), Matchers.is(true));

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

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.GREATER_THAN.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.GREATER_THAN.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void greaterThan_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.GREATER_THAN.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.MULTIPLICATION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(30L));

    }

    @Test
    public void multiplication_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 6L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.MULTIPLICATION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(30L));

    }

    @Test
    public void multiplication_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 6f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.MULTIPLICATION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Double.class));
        MatcherAssert.assertThat((Double) result.value(), Matchers.is(30.0));

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

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.MULTIPLICATION.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.MULTIPLICATION.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplication_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.MULTIPLICATION.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.DIVISION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(6L));

    }

    @Test
    public void division_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.DIVISION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(6L));

    }

    @Test
    public void division_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 30.0f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.DIVISION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Double.class));
        MatcherAssert.assertThat((Double) result.value(), Matchers.is(6.0));

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

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.DIVISION.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.DIVISION.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void division_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.DIVISION.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.ADDITION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) String.class));
        MatcherAssert.assertThat((String) result.value(), Matchers.is("test5"));


    }

    @Test
    public void addition_doOperation_withValidOperands_TwoLongOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Long.class, 5L);

        Operand result = OperationType.ADDITION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(35L));

    }

    @Test
    public void addition_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.ADDITION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(35L));

    }

    @Test
    public void addition_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 30.0f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.ADDITION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Double.class));
        MatcherAssert.assertThat((Double) result.value(), Matchers.is(35.0));

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


    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.ADDITION.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.ADDITION.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void addition_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.ADDITION.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

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

        Operand result = OperationType.SUBTRACTION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(25L));

    }

    @Test
    public void subtraction_doOperation_withValidOperands_LongAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Long.class, 30L);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.SUBTRACTION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Long.class));
        MatcherAssert.assertThat((Long) result.value(), Matchers.is(25L));

    }

    @Test
    public void subtraction_doOperation_withValidOperands_FloatAndIntegerOperands_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Float.class, 30.0f);
        Operand operand2 = OperandFactory.createOperationResult(Integer.class, 5);

        Operand result = OperationType.SUBTRACTION.doOperation(operand1, operand2);

        MatcherAssert.assertThat(result.getOperandsJavaType(), Matchers.equalTo((Class) Double.class));
        MatcherAssert.assertThat((Double) result.value(), Matchers.is(25.0));

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


    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withOneNullValuedOperand1_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.SUBTRACTION.doOperation(operand1, new NullValueOperand("null")).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withOneNullValuedOperand2_Test() {

        Operand operand1 = OperandFactory.createOperationResult(Boolean.class, true);
        OperationType.SUBTRACTION.doOperation(new NullValueOperand("null"), operand1).value();

    }

    @Test(expected = IllegalArgumentException.class)
    public void subtraction_doOperation_withTwoNullValuedOperand_Test() {

        OperationType.SUBTRACTION.doOperation(new NullValueOperand("null"), new NullValueOperand("null")).value();

    }

    // --------------------------------------------------------
    // -- COMMON UTILITY FUNCTIONS TESTS
    // --------------------------------------------------------


    @Test
    public void convertToLong_nullSafety() {

        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(null), Matchers.nullValue());

        Operand operand = Mockito.mock(Operand.class);
        Mockito.when(operand.getOperandsJavaType()).thenReturn(null);
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.nullValue());

    }

    @Test
    public void convertToLong_incompatibleJavaType_shouldReturnNull() {

        // Non numeric type
        Operand operand = Mockito.mock(Operand.class);
        Mockito.when(operand.getOperandsJavaType()).thenReturn(String.class);
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.nullValue());

        // floating point type
        Mockito.when(operand.getOperandsJavaType()).thenReturn(Double.class);
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.nullValue());

    }

    @Test
    public void convertToLong_usingShortValue() {

        // short
        Operand operand = Mockito.mock(Operand.class);
        Mockito.when(operand.getOperandsJavaType()).thenReturn(short.class);
        Mockito.when(operand.value()).thenReturn(Short.parseShort("1"));
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.is(1L));


        // Short
        Mockito.when(operand.getOperandsJavaType()).thenReturn(Short.class);
        Mockito.when(operand.value()).thenReturn(Short.valueOf(Short.parseShort("1")));
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.is(1L));


    }

    @Test
    public void convertToLong_usingIntegerValue() {

        // short
        Operand operand = Mockito.mock(Operand.class);
        Mockito.when(operand.getOperandsJavaType()).thenReturn(int.class);
        Mockito.when(operand.value()).thenReturn(Integer.parseInt("1"));
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.is(1L));


        // Short
        Mockito.when(operand.getOperandsJavaType()).thenReturn(int.class);
        Mockito.when(operand.value()).thenReturn(Integer.valueOf(Integer.parseInt("1")));
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.is(1L));


    }

    @Test
    public void convertToLong_usingLongValue() {

        // short
        Operand operand = Mockito.mock(Operand.class);
        Mockito.when(operand.getOperandsJavaType()).thenReturn(long.class);
        Mockito.when(operand.value()).thenReturn(Long.parseLong("1"));
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.is(1L));


        // Short
        Mockito.when(operand.getOperandsJavaType()).thenReturn(Long.class);
        Mockito.when(operand.value()).thenReturn(Long.valueOf(Long.parseLong("1")));
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.is(1L));


    }


    @Test
    public void convertToDouble_nullSafety() {

        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(null), Matchers.nullValue());

        Operand operand = Mockito.mock(Operand.class);
        Mockito.when(operand.getOperandsJavaType()).thenReturn(null);
        MatcherAssert.assertThat(OperationType.SUBTRACTION.convertToLong(operand), Matchers.nullValue());

    }

}
