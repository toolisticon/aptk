package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

import java.util.regex.Matcher;

/**
 * String based operand.
 */
public class StringOperand extends Operand<String> {

    private final String internalValue;

    public StringOperand(String expressionString) {
        super(OperandType.STRING, expressionString);


        Matcher matcher = getOperandType().getOperandPattern().matcher(expressionString);
        if (matcher.find()) {

            String tempValue = matcher.group(1);

            // replace escaped single quotes
            tempValue = tempValue.replaceAll("\\\\[']", "'");

            // replace escaped escape chars
            tempValue = tempValue.replaceAll("\\\\", "\\");


            internalValue = tempValue;

        } else {
            internalValue = null;
        }


    }

    @Override
    public Class<String> getOperandsJavaType() {
        return String.class;
    }

    @Override
    public String value() {
        return internalValue;
    }


}
