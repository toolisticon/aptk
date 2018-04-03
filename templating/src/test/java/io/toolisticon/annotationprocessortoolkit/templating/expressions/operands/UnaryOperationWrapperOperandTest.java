package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import io.toolisticon.annotationprocessortoolkit.templating.expressions.operations.OperationType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link UnaryOperationWrapperOperand}.
 */
public class UnaryOperationWrapperOperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_nullValuedOperationType_mustThrowException() {

        new UnaryOperationWrapperOperand(new BooleanOperand("true"), null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_binaryOperationType_mustThrowException() {

        new UnaryOperationWrapperOperand(new BooleanOperand("true"), OperationType.DIVISION);

    }

    @Test
    public void test_unaryOperationType_mustNotThrowException() {

        new UnaryOperationWrapperOperand(new BooleanOperand("true"), OperationType.NEGATE);

    }

    @Test
    public void test_unaryOperationType_successfulPath_getValue() {

        UnaryOperationWrapperOperand unit = new UnaryOperationWrapperOperand(new BooleanOperand("true"), OperationType.NEGATE);
        MatcherAssert.assertThat((Boolean) unit.value(), Matchers.is(false));

        unit = new UnaryOperationWrapperOperand(new BooleanOperand("false"), OperationType.NEGATE);
        MatcherAssert.assertThat((Boolean) unit.value(), Matchers.is(true));


    }

    @Test
    public void test_unaryOperationType_successfulPath_getOperandsJavaType() {

        UnaryOperationWrapperOperand unit = new UnaryOperationWrapperOperand(new BooleanOperand("true"), OperationType.NEGATE);
        MatcherAssert.assertThat( (Class)unit.getOperandsJavaType(), Matchers.equalTo((Class)Boolean.class));



    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nullValuedOperand_mustThrowException() {

        UnaryOperationWrapperOperand unit = new UnaryOperationWrapperOperand(null, OperationType.NEGATE);
        MatcherAssert.assertThat(unit.value(), Matchers.nullValue());


    }

}
