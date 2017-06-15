package de.holisticon.example.annotationprocessortoolkit.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class MethodWithOneStringParameterAndVoidReturnTypeProcessorTest extends AbstractAnnotationProcessorTest<MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor> {


    public MethodWithOneStringParameterAndVoidReturnTypeProcessorTest(String description, String resource, String[] errors, String[] warnings) {
        super(description, resource, errors, warnings);
    }

    @Override
    protected MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor getAnnotationProcessor() {
        return new MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor();
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {"Test valid usage", "testcases/methodWithOneStringParameterAndVoidReturn/ValidUsageTest.java", new String[]{}, new String[]{}},
                {"Test invalid usage : non void return type", "testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonVoidReturnType.java", new String[]{"Method must have void return type"}, new String[]{}},
                {"Test invalid usage : non String parameter", "testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonStringParameter.java", new String[]{"Method must have parameters of types [java.lang.String], but has parameters of types [java.lang.Object]"}, new String[]{}},

        });

    }


    @Test
    public void testCorrectnessOfAdviceArgumentAnnotation() {
        super.test();
    }


}