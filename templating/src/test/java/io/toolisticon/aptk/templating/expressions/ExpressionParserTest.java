package io.toolisticon.aptk.templating.expressions;

import io.toolisticon.aptk.templating.expressions.operands.ExpressionOperand;
import io.toolisticon.aptk.templating.expressions.operands.Operand;
import io.toolisticon.aptk.templating.expressions.operands.OperandType;
import io.toolisticon.aptk.templating.expressions.operations.OperationType;
import io.toolisticon.aptk.templating.expressions.operations.OperationTypeSearchResult;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class ExpressionParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void getOperationType_operationTypeNotFound() {

        ExpressionParser.getOperationType("   ==   sdsadasda", 12);

    }

    @Test
    public void getOperationType_getEquals() {

        assertOperationTypeSearchResult(ExpressionParser.getOperationType("   ==   sdsadasda", 0), 0, 8, OperationType.EQUAL);
        assertOperationTypeSearchResult(ExpressionParser.getOperationType("   ==   sdsadasda", 2), 2, 8, OperationType.EQUAL);


    }

    private void assertOperationTypeSearchResult(OperationTypeSearchResult result, int start, int end, OperationType operationType) {

        MatcherAssert.assertThat(result.getStartIndex(), Matchers.is(start));
        MatcherAssert.assertThat(result.getEndIndex(), Matchers.is(end));
        MatcherAssert.assertThat(result.getValue(), Matchers.is(operationType));

    }

    @Test
    public void parseExpression_parseNegation_negatedTrue_Test() {
        Expression expression = ExpressionParser.parseExpression("!true");

        MatcherAssert.assertThat(expression.getOperands().length, Matchers.is(1));
        MatcherAssert.assertThat(expression.getOperationTypes().length, Matchers.is(0));

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(false));

    }


    @Test
    public void parseExpression_parseNegation_negatedFalse_Test() {
        Expression expression = ExpressionParser.parseExpression("!false");

        MatcherAssert.assertThat(expression.getOperands().length, Matchers.is(1));
        MatcherAssert.assertThat(expression.getOperationTypes().length, Matchers.is(0));

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }

    @Test
    public void parseExpression_parseNegation_bracesA_Test() {

        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("'' + (40 * (2+3)) == '200'").evaluateExpression().value(), Matchers.is(true));


        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("!(!false || !true)").evaluateExpression().value(), Matchers.is(false));
        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("!(!false || (!true || true))").evaluateExpression().value(), Matchers.is(false));
        MatcherAssert.assertThat((Long) ExpressionParser.parseExpression("(40 * (2+3))").evaluateExpression().value(), Matchers.is(200L));
        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("40 * (2+3) == 200").evaluateExpression().value(), Matchers.is(true));
        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("40 * (2+3) == 200.0 || false && true").evaluateExpression().value(), Matchers.is(true));
        MatcherAssert.assertThat((String) ExpressionParser.parseExpression("'' + (40 * (2+3))").evaluateExpression().value(), Matchers.is("200"));
        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("'' + (40 * (2+3)) == '200'").evaluateExpression().value(), Matchers.is(true));

        MatcherAssert.assertThat((Boolean) ExpressionParser.parseExpression("'' + (40 * (2+3)) == '200' || false && true").evaluateExpression().value(), Matchers.is(true));


    }

    @Test
    public void parseExpression_parseString_simpleExpressionWithoutBraces() {

        Expression expression = ExpressionParser.parseExpression("'abv' == 1 && true != false || 1.4 > 1.2 >= 1 < abc.def <= abc");

        MatcherAssert.assertThat(expression.getOperands().length, Matchers.is(9));
        MatcherAssert.assertThat(expression.getOperationTypes().length, Matchers.is(8));

        MatcherAssert.assertThat(((Operand) (expression.getOperands()[0])).getOperandType(), Matchers.is(OperandType.STRING));

        MatcherAssert.assertThat(expression.getOperationTypes()[0], Matchers.is(OperationType.EQUAL));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[1])).getOperandType(), Matchers.is(OperandType.LONG));

        MatcherAssert.assertThat(expression.getOperationTypes()[1], Matchers.is(OperationType.AND));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[2])).getOperandType(), Matchers.is(OperandType.BOOLEAN));

        MatcherAssert.assertThat(expression.getOperationTypes()[2], Matchers.is(OperationType.NOT_EQUAL));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[3])).getOperandType(), Matchers.is(OperandType.BOOLEAN));

        MatcherAssert.assertThat(expression.getOperationTypes()[3], Matchers.is(OperationType.OR));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[4])).getOperandType(), Matchers.is(OperandType.DOUBLE));

        MatcherAssert.assertThat(expression.getOperationTypes()[4], Matchers.is(OperationType.GREATER_THAN));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[5])).getOperandType(), Matchers.is(OperandType.DOUBLE));

        MatcherAssert.assertThat(expression.getOperationTypes()[5], Matchers.is(OperationType.GREATER_OR_EQUAL_THAN));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[6])).getOperandType(), Matchers.is(OperandType.LONG));

        MatcherAssert.assertThat(expression.getOperationTypes()[6], Matchers.is(OperationType.LESS_THAN));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[7])).getOperandType(), Matchers.is(OperandType.DYNAMIC_VALUE));

        MatcherAssert.assertThat(expression.getOperationTypes()[7], Matchers.is(OperationType.LESS_OR_EQUAL_THAN));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[8])).getOperandType(), Matchers.is(OperandType.DYNAMIC_VALUE));
    }

    @Test
    public void parseExpression_parseString_complexExpressionWithBraces() {

        Expression expression = ExpressionParser.parseExpression("(true || false) && ( (true && true) || false )");

        MatcherAssert.assertThat(expression.getOperands().length, Matchers.is(2));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[0])).getOperandType(), Matchers.is(OperandType.EXPRESSION));
        MatcherAssert.assertThat(expression.getOperationTypes()[0], Matchers.is(OperationType.AND));
        MatcherAssert.assertThat(((Operand) (expression.getOperands()[1])).getOperandType(), Matchers.is(OperandType.EXPRESSION));

        // Expression 1 : true || false
        Expression expression1 = ((ExpressionOperand) expression.getOperands()[0]).getExpression();
        MatcherAssert.assertThat(((Operand) (expression1.getOperands()[0])).getOperandType(), Matchers.is(OperandType.BOOLEAN));
        MatcherAssert.assertThat(expression1.getOperationTypes()[0], Matchers.is(OperationType.OR));
        MatcherAssert.assertThat(((Operand) (expression1.getOperands()[1])).getOperandType(), Matchers.is(OperandType.BOOLEAN));

        // Expression 2 : (true && true) || false
        Expression expression2 = ((ExpressionOperand) expression.getOperands()[1]).getExpression();
        MatcherAssert.assertThat(((Operand) (expression2.getOperands()[0])).getOperandType(), Matchers.is(OperandType.EXPRESSION));
        MatcherAssert.assertThat(expression2.getOperationTypes()[0], Matchers.is(OperationType.OR));
        MatcherAssert.assertThat(((Operand) (expression2.getOperands()[1])).getOperandType(), Matchers.is(OperandType.BOOLEAN));

        // Expression 3 : true && true
        Expression expression3 = (Expression) ((ExpressionOperand) expression2.getOperands()[0]).getExpression();

        MatcherAssert.assertThat(((Operand) (expression3.getOperands()[0])).getOperandType(), Matchers.is(OperandType.BOOLEAN));
        MatcherAssert.assertThat(expression3.getOperationTypes()[0], Matchers.is(OperationType.AND));
        MatcherAssert.assertThat(((Operand) (expression3.getOperands()[1])).getOperandType(), Matchers.is(OperandType.BOOLEAN));


    }

    @Test
    public void evaluateExpression_withModel_equalWithTwoDynamicNullValues() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", null);
        model.put("value2", null);

        Expression expression = ExpressionParser.parseExpression("value1 == value2", model);

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));


    }

    @Test
    public void evaluateExpression_withModel_equalOneDynamicNullValue1() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", null);

        Expression expression = ExpressionParser.parseExpression("value1 == value2", model);

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(false));


    }

    @Test
    public void evaluateExpression_withModel_equalOneDynamicNullValue2() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", null);
        model.put("value2", 5L);

        Expression expression = ExpressionParser.parseExpression("value1 == value2", model);

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(false));


    }


    @Test
    public void evaluateExpression_withModel_equalOperands() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 5L);

        Expression expression = ExpressionParser.parseExpression("value1 == value2", model);

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));


    }

    @Test
    public void evaluateExpression_withModel_equalOperandsWithDifferentTypes() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 5);

        Expression expression = ExpressionParser.parseExpression("value1 == value2", model);

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));


    }

    @Test
    public void evaluateExpression_withModel_notEqualOperands() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 6L);

        Expression expression = ExpressionParser.parseExpression("value1 == value2", model);

        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(false));


    }

    @Test
    public void evaluateExpression_withModel_moreComplexMixedExpression() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 6L);

        Expression expression = ExpressionParser.parseExpression("(value1 == 5) && (value2 == 6.0)", model);
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

        expression = ExpressionParser.parseExpression("(value1 == 5) && (value2 == 6.0)", model);
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));


    }


    @Test
    public void evaluateExpression_withModel_evenMoreComplexMixedExpression() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 6L);
        model.put("value3", null);

        Expression expression = ExpressionParser.parseExpression("(value1 == 5) || value3 != null && (value2 == 6.0) ", model);
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }


    @Test
    public void evaluateExpression_withModel_evenMoreComplexMixedExpressionWithStringConcatenation() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 6L);
        model.put("value3", null);
        model.put("value4", "Text");

        Expression expression = ExpressionParser.parseExpression("(value1 == 5) || value3 != null && (value2 == 6.0 ) && value4 + '1' == 'Text1'", model);
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }


    @Test
    public void evaluateExpression_withModel_testVariationOfWhitespaces() {


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value1", 5L);
        model.put("value2", 6L);
        model.put("value3", null);
        Expression expression = ExpressionParser.parseExpression("    (value1==5 +6 -2*3)  ||value3 !=null      && (    value2 *3 ==18.0  ) ", model);
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));


    }


    // ------------------------------------------------------------------------------------------------------
    // -- Test handling of whitespaces
    // ------------------------------------------------------------------------------------------------------


    @Test
    public void evaluateExpression_testLeadingWhitespaces() {

        Expression expression = ExpressionParser.parseExpression("    5==5");
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }


    @Test
    public void evaluateExpression_testTrailingWhitespaces() {

        Expression expression = ExpressionParser.parseExpression("5==5     ");
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }

    @Test
    public void evaluateExpression_testLeadingWhitespacesInBraces() {

        Expression expression = ExpressionParser.parseExpression("(    5==5)");
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }


    @Test
    public void evaluateExpression_testTrailingWhitespacesInBraces() {

        Expression expression = ExpressionParser.parseExpression("(5==5     )");
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }

    @Test
    public void evaluateExpression_testInbetweenWhitespaces1() {

        Expression expression = ExpressionParser.parseExpression("5==      5");
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }

    @Test
    public void evaluateExpression_testInbetweenWhitespaces2() {

        Expression expression = ExpressionParser.parseExpression("5        ==5");
        MatcherAssert.assertThat((Boolean) expression.evaluateExpression().value(), Matchers.is(true));

    }


}
