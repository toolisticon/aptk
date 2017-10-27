package de.holisticon.annotationprocessortoolkit.templating.expression;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Expression;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.OperandFactory;
import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.UnaryOperationWrapperOperand;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link de.holisticon.annotationprocessortoolkit.templating.expressions.Expression}.
 */
public class ExpressionTest {

    // ------------------------------------------------------
    // -- Do test with exactly one operand and no operation
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithSingleOperand_Test() {
        doTestSingleOperand(Boolean.class, true);
        doTestSingleOperand(Boolean.class, false);
        doTestSingleOperand(Long.class, 5L);
        doTestSingleOperand(Integer.class, 5);
        doTestSingleOperand(Float.class, 5.0f);
        doTestSingleOperand(Double.class, 5.0);
    }


    private <T> void doTestSingleOperand(Class<T> type, T value) {

        Operand[] operands = getArray(OperandFactory.createOperationResult(type, value));
        OperationType[] operationTypes = new OperationType[0];

        Expression givenExpression = new Expression(operands, operationTypes);

        MatcherAssert.assertThat((T) (givenExpression.evaluateExpression().value()), Matchers.is(value));

    }

    // ------------------------------------------------------
    // ------------------------------------------------------
    // -- Do test with multiple operands and operations
    // ------------------------------------------------------
    // ------------------------------------------------------


    // ------------------------------------------------------
    // -- ADDITION
    // ------------------------------------------------------


    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_add_withDecimals_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.ADDITION, createOperand(Long.class, 10L), 15L);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.ADDITION, createOperand(Integer.class, 10), 15L);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.ADDITION, createOperand(Long.class, 10L), 15L);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_add_withFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.ADDITION, createOperand(Double.class, 10.0), 15.0);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.ADDITION, createOperand(Float.class, 10.0f), 15.0);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.ADDITION, createOperand(Double.class, 10.0), 15.0);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_add_withMixedDecimalsAndFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.ADDITION, createOperand(Double.class, 10.0), 15.0);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.ADDITION, createOperand(Double.class, 10.0), 15.0);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.ADDITION, createOperand(Float.class, 10.0f), 15.0);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.ADDITION, createOperand(Float.class, 10.0f), 15.0);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.ADDITION, createOperand(Integer.class, 10), 15.0);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.ADDITION, createOperand(Integer.class, 10), 15.0);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.ADDITION, createOperand(Long.class, 10L), 15.0);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.ADDITION, createOperand(Long.class, 10L), 15.0);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_add_withStrings_Test() {

        doTestSingleOperation(createOperand(String.class, "It"), OperationType.ADDITION, createOperand(String.class, " works!"), "It works!");
        doTestSingleOperation(createOperand(String.class, "It"), OperationType.ADDITION, createOperand(Integer.class, 1), "It1");
        doTestSingleOperation(createOperand(Integer.class, 1), OperationType.ADDITION, createOperand(String.class, "works"), "1works");

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_add_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.ADDITION, createOperand(Double.class, 10.0), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_add_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(ExpressionTest.class, this), OperationType.ADDITION, createOperand(Double.class, 10.0), 15.0);

    }

    // ------------------------------------------------------
    // -- SUBTRACTION
    // ------------------------------------------------------


    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_subtraction_withDecimals_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.SUBTRACTION, createOperand(Long.class, 10L), -5L);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.SUBTRACTION, createOperand(Integer.class, 10), -5L);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.SUBTRACTION, createOperand(Long.class, 10L), -5L);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_subtraction_withFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.SUBTRACTION, createOperand(Double.class, 10.0), -5.0);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.SUBTRACTION, createOperand(Float.class, 10.0f), -5.0);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.SUBTRACTION, createOperand(Double.class, 10.0), -5.0);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_subtraction_withMixedDecimalsAndFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.SUBTRACTION, createOperand(Double.class, 10.0), -5.0);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.SUBTRACTION, createOperand(Double.class, 10.0), -5.0);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.SUBTRACTION, createOperand(Float.class, 10.0f), -5.0);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.SUBTRACTION, createOperand(Float.class, 10.0f), -5.0);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.SUBTRACTION, createOperand(Integer.class, 10), -5.0);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.SUBTRACTION, createOperand(Integer.class, 10), -5.0);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.SUBTRACTION, createOperand(Long.class, 10L), -5.0);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.SUBTRACTION, createOperand(Long.class, 10L), -5.0);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_subtraction_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.SUBTRACTION, createOperand(Double.class, 10.0), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_subtraction_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(ExpressionTest.class, this), OperationType.SUBTRACTION, createOperand(Double.class, 10.0), 15.0);

    }

    // ------------------------------------------------------
    // -- MULTIPLICATION
    // ------------------------------------------------------


    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_multiplication_withDecimals_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.MULTIPLICATION, createOperand(Long.class, 10L), 50L);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.MULTIPLICATION, createOperand(Integer.class, 10), 50L);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.MULTIPLICATION, createOperand(Long.class, 10L), 50L);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_multiplication_withFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.MULTIPLICATION, createOperand(Double.class, 10.0), 50.0);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.MULTIPLICATION, createOperand(Float.class, 10.0f), 50.0);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.MULTIPLICATION, createOperand(Double.class, 10.0), 50.0);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_multiplication_withMixedDecimalsAndFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.MULTIPLICATION, createOperand(Double.class, 10.0), 50.0);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.MULTIPLICATION, createOperand(Double.class, 10.0), 50.0);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.MULTIPLICATION, createOperand(Float.class, 10.0f), 50.0);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.MULTIPLICATION, createOperand(Float.class, 10.0f), 50.0);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.MULTIPLICATION, createOperand(Integer.class, 10), 50.0);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.MULTIPLICATION, createOperand(Integer.class, 10), 50.0);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.MULTIPLICATION, createOperand(Long.class, 10L), 50.0);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.MULTIPLICATION, createOperand(Long.class, 10L), 50.0);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_multiplication_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.MULTIPLICATION, createOperand(Double.class, 10.0), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_multiplication_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(ExpressionTest.class, this), OperationType.MULTIPLICATION, createOperand(Double.class, 10.0), 15.0);

    }


    // ------------------------------------------------------
    // -- DIVISION
    // ------------------------------------------------------


    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_division_withDecimals_Test() {

        doTestSingleOperation(createOperand(Integer.class, 150), OperationType.DIVISION, createOperand(Long.class, 10L), 15L);
        doTestSingleOperation(createOperand(Integer.class, 150), OperationType.DIVISION, createOperand(Integer.class, 10), 15L);
        doTestSingleOperation(createOperand(Long.class, 150L), OperationType.DIVISION, createOperand(Long.class, 10L), 15L);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_division_withFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Float.class, 150.0f), OperationType.DIVISION, createOperand(Double.class, 10.0), 15.0);
        doTestSingleOperation(createOperand(Float.class, 150.0f), OperationType.DIVISION, createOperand(Float.class, 10.0f), 15.0);
        doTestSingleOperation(createOperand(Double.class, 150.0), OperationType.DIVISION, createOperand(Double.class, 10.0), 15.0);

    }

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_division_withMixedDecimalsAndFloatingPoints_Test() {

        doTestSingleOperation(createOperand(Integer.class, 150), OperationType.DIVISION, createOperand(Double.class, 10.0), 15.0);
        doTestSingleOperation(createOperand(Long.class, 150L), OperationType.DIVISION, createOperand(Double.class, 10.0), 15.0);

        doTestSingleOperation(createOperand(Integer.class, 150), OperationType.DIVISION, createOperand(Float.class, 10.0f), 15.0);
        doTestSingleOperation(createOperand(Long.class, 150L), OperationType.DIVISION, createOperand(Float.class, 10.0f), 15.0);

        doTestSingleOperation(createOperand(Double.class, 150.0), OperationType.DIVISION, createOperand(Integer.class, 10), 15.0);
        doTestSingleOperation(createOperand(Double.class, 150.0), OperationType.DIVISION, createOperand(Integer.class, 10), 15.0);

        doTestSingleOperation(createOperand(Float.class, 150.0f), OperationType.DIVISION, createOperand(Long.class, 10L), 15.0);
        doTestSingleOperation(createOperand(Float.class, 150.0f), OperationType.DIVISION, createOperand(Long.class, 10L), 15.0);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_division_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.DIVISION, createOperand(Double.class, 10.0), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_division_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(ExpressionTest.class, this), OperationType.DIVISION, createOperand(Double.class, 10.0), 15.0);

    }


    // ------------------------------------------------------
    // -- LESS THAN
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_lessThan_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);

        // Cross type operation
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);


        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);


        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_THAN, createOperand(Double.class, 5.0), false);


        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_THAN, createOperand(Float.class, 5.0f), false);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_lessThan_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.LESS_THAN, createOperand(Integer.class, 15), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_lessThan_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(Integer.class, 15), OperationType.LESS_THAN, createOperand(Boolean.class, true), 15.0);

    }

    // ------------------------------------------------------
    // -- LESS OR EQUAL THAN
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_lessOrEqualThan_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), false);

        // Cross type operation
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), false);


        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), false);


        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 10.0), true);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.LESS_OR_EQUAL_THAN, createOperand(Double.class, 5.0), false);


        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 10L), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 10), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), true);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.LESS_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), false);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_lessOrEqualThan_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.LESS_OR_EQUAL_THAN, createOperand(Integer.class, 15), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_lessOrEqualThan_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(Integer.class, 15), OperationType.LESS_OR_EQUAL_THAN, createOperand(Boolean.class, true), 15.0);

    }


    // ------------------------------------------------------
    // -- LESS THAN
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_greaterThan_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), true);

        // Cross type operation
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), true);


        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), true);


        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), false);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_THAN, createOperand(Double.class, 5.0), true);


        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_THAN, createOperand(Float.class, 5.0f), true);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_greaterThan_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.GREATER_THAN, createOperand(Integer.class, 15), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_greaterThan_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(Integer.class, 15), OperationType.GREATER_THAN, createOperand(Boolean.class, true), 15.0);

    }

    // ------------------------------------------------------
    // -- LESS OR EQUAL THAN
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_greaterOrEqualThan_Test() {

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);

        // Cross type operation
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Integer.class, 5), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Integer.class, 10), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);


        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Long.class, 5L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Long.class, 10L), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);


        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);
        doTestSingleOperation(createOperand(Float.class, 5.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 10.0), false);
        doTestSingleOperation(createOperand(Float.class, 10.0f), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Double.class, 5.0), true);


        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 10L), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 10), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Double.class, 5.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 10.0f), false);
        doTestSingleOperation(createOperand(Double.class, 10.0), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Float.class, 5.0f), true);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_greaterOrEqualThan_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Integer.class, 15), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_greaterOrEqualThan_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(Integer.class, 15), OperationType.GREATER_OR_EQUAL_THAN, createOperand(Boolean.class, true), 15.0);

    }


    // ------------------------------------------------------
    // -- BOOLEAN AND
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_and_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.AND, createOperand(Boolean.class, true), true);
        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.AND, createOperand(Boolean.class, false), false);
        doTestSingleOperation(createOperand(Boolean.class, false), OperationType.AND, createOperand(Boolean.class, true), false);
        doTestSingleOperation(createOperand(Boolean.class, false), OperationType.AND, createOperand(Boolean.class, false), false);

    }


    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_and_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.AND, createOperand(Integer.class, 15), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_end_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(Integer.class, 15), OperationType.AND, createOperand(Boolean.class, true), 15.0);

    }

    // ------------------------------------------------------
    // -- EQUAL
    // ------------------------------------------------------

    private static class EqualTestClassWithoutEqualAndHashcodeImplementation {

    }

    private static class EqualTestClassWithEqualAndHashcodeImplementation {

        private final int value;

        public EqualTestClassWithEqualAndHashcodeImplementation(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EqualTestClassWithEqualAndHashcodeImplementation that = (EqualTestClassWithEqualAndHashcodeImplementation) o;

            return value == that.value;

        }

        @Override
        public int hashCode() {
            return value;
        }
    }


    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_equal_Test() {

        // equality tests
        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.EQUAL, createOperand(Boolean.class, true), true);
        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.EQUAL, createOperand(Boolean.class, false), false);

        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Integer.class, 4), true);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Integer.class, 5), false);

        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Long.class, 4L), true);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Long.class, 5L), false);

        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Float.class, 4.0f), true);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Float.class, 5.0f), false);

        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Double.class, 4.0), true);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Double.class, 5.0), false);

        doTestSingleOperation(createOperand(String.class, "4"), OperationType.EQUAL, createOperand(String.class, "4"), true);
        doTestSingleOperation(createOperand(String.class, "4"), OperationType.EQUAL, createOperand(String.class, "5"), false);

        Object testObjectEquality = new EqualTestClassWithoutEqualAndHashcodeImplementation();
        doTestSingleOperation(createOperand(Object.class, testObjectEquality), OperationType.EQUAL, createOperand(Object.class, testObjectEquality), true);
        doTestSingleOperation(createOperand(Object.class, new EqualTestClassWithoutEqualAndHashcodeImplementation()), OperationType.EQUAL, createOperand(Object.class, new EqualTestClassWithoutEqualAndHashcodeImplementation()), false);

        doTestSingleOperation(createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(5)), OperationType.EQUAL, createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(5)), true);
        doTestSingleOperation(createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(5)), OperationType.EQUAL, createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(10)), false);

        // cross type operation
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Long.class, 4L), true);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Float.class, 4.0f), true);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Double.class, 4.0), true);

        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.EQUAL, createOperand(Double.class, 5.0), false);

        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Integer.class, 4), true);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Float.class, 4.0f), true);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Double.class, 4.0), true);

        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.EQUAL, createOperand(Double.class, 5.0), false);

        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Long.class, 4L), true);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Integer.class, 4), true);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Double.class, 4.0), true);

        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Integer.class, 5), false);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.EQUAL, createOperand(Double.class, 5.0), false);

        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Long.class, 4L), true);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Float.class, 4.0f), true);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Integer.class, 4), true);

        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Long.class, 5L), false);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Float.class, 5.0f), false);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.EQUAL, createOperand(Integer.class, 5), false);

    }

    // ------------------------------------------------------
    // -- NOT EQUAL
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_notEqual_Test() {

        // equality tests
        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.NOT_EQUAL, createOperand(Boolean.class, true), false);
        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.NOT_EQUAL, createOperand(Boolean.class, false), true);

        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Integer.class, 4), false);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Integer.class, 5), true);

        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Long.class, 4L), false);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Long.class, 5L), true);

        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Float.class, 4.0f), false);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Float.class, 5.0f), true);

        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Double.class, 4.0), false);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Double.class, 5.0), true);

        doTestSingleOperation(createOperand(String.class, "4"), OperationType.NOT_EQUAL, createOperand(String.class, "4"), false);
        doTestSingleOperation(createOperand(String.class, "4"), OperationType.NOT_EQUAL, createOperand(String.class, "5"), true);

        Object testObjectEquality = new EqualTestClassWithoutEqualAndHashcodeImplementation();
        doTestSingleOperation(createOperand(Object.class, testObjectEquality), OperationType.NOT_EQUAL, createOperand(Object.class, testObjectEquality), false);
        doTestSingleOperation(createOperand(Object.class, new EqualTestClassWithoutEqualAndHashcodeImplementation()), OperationType.NOT_EQUAL, createOperand(Object.class, new EqualTestClassWithoutEqualAndHashcodeImplementation()), true);

        doTestSingleOperation(createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(5)), OperationType.NOT_EQUAL, createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(5)), false);
        doTestSingleOperation(createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(5)), OperationType.NOT_EQUAL, createOperand(Object.class, new EqualTestClassWithEqualAndHashcodeImplementation(10)), true);

        // cross type operation
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Long.class, 4L), false);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Float.class, 4.0f), false);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Double.class, 4.0), false);

        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Integer.class, 4), OperationType.NOT_EQUAL, createOperand(Double.class, 5.0), true);

        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Integer.class, 4), false);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Float.class, 4.0f), false);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Double.class, 4.0), false);

        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Long.class, 4L), OperationType.NOT_EQUAL, createOperand(Double.class, 5.0), true);

        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Long.class, 4L), false);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Integer.class, 4), false);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Double.class, 4.0), false);

        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Integer.class, 5), true);
        doTestSingleOperation(createOperand(Float.class, 4.0f), OperationType.NOT_EQUAL, createOperand(Double.class, 5.0), true);

        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Long.class, 4L), false);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Float.class, 4.0f), false);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Integer.class, 4), false);

        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Long.class, 5L), true);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Float.class, 5.0f), true);
        doTestSingleOperation(createOperand(Double.class, 4.0), OperationType.NOT_EQUAL, createOperand(Integer.class, 5), true);

    }

    // ------------------------------------------------------
    // -- BOOLEAN OR
    // ------------------------------------------------------

    @Test
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_or_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.OR, createOperand(Boolean.class, true), true);
        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.OR, createOperand(Boolean.class, false), true);
        doTestSingleOperation(createOperand(Boolean.class, false), OperationType.OR, createOperand(Boolean.class, true), true);
        doTestSingleOperation(createOperand(Boolean.class, false), OperationType.OR, createOperand(Boolean.class, false), false);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_or_withInvalidType1_Test() {

        doTestSingleOperation(createOperand(Boolean.class, true), OperationType.OR, createOperand(Integer.class, 15), 15.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_ExpressionWithTwoOperandsAndOneOperation_or_withInvalidType2_Test() {

        doTestSingleOperation(createOperand(Integer.class, 15), OperationType.OR, createOperand(Boolean.class, true), 15.0);

    }

    // ------------------------------------------------------
    // -- NEGATE
    // ------------------------------------------------------


    @Test
    public void evaluate_negate_Test() {

        MatcherAssert.assertThat((Boolean) createUnaryOperand(OperationType.NEGATE, true).value(), Matchers.is(false));
        MatcherAssert.assertThat((Boolean) createUnaryOperand(OperationType.NEGATE, false).value(), Matchers.is(true));

    }

    private Operand createUnaryOperand(OperationType operationType, Boolean value) {
        return OperandFactory.createOperand(OperandType.BOOLEAN, "" + value, getArray(OperationType.NEGATE), null);

    }


    // ------------------------------------------------------
    // -- Common stuff
    // ------------------------------------------------------

    private <T> void doTestSingleOperation(Operand operand1, OperationType operationType, Operand operand2, Object expectedResult) {

        Operand[] operands = getArray(operand1, operand2);
        OperationType[] operationTypes = getArray(operationType);

        Expression givenExpression = new Expression(operands, operationTypes);

        MatcherAssert.assertThat((T) (givenExpression.evaluateExpression().value()), Matchers.is(expectedResult));

    }


    private <T> Operand createOperand(Class<T> type, T value) {
        return OperandFactory.createOperationResult(type, value);
    }


    private <T> T[] getArray(T... values) {
        return values;
    }

}
