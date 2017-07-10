---
layout: default
used_in_navigation: true
menu_name: Testing Annotation Processors
order: 3
---

# Testing Annotation Processors

Testing of annotation processors is very tricky. Your annotation processors heavily rely on the _ProcessingEnvironment_ tools.
Those tools offer you access to the java compile time model and cannot be mocked or simulated very easily in unit tests.

So best way to test your annotation processors is to actually do a compile time test. Doing this all tools offered by the _ProcessingEnvironment_ are provided by the compiler.
This can be done by using the com.google.testing.compile compile-testing framework which allows to do compilations of some test source code during your tests and to check the outcomes.
One thing to mention is that JDK >=6 is supported up to version 0.9 - all following versions are base on JDK >=8.

This project simplifies testing even more by hiding the complexity of using the compile-testing framework.

We will show you what needs to be done in the following...

## Setting up a test

Setting up a junit test is quite easy. This framework is using parameterized unit tests to do some testing.

Here's a small example for a testcase that validates the compile outcome. This is quite useful for annotation processors that are doing validations about the usage of annotations:

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

So basically you need to do the following things:

1. Create a test class which should extend the abstract _AbstractAnnotationProcessorTest<YourAnnotationPracessor>_ class
2. Implement the abstract method _getAnnotationProcessor_ declared by the  _AbstractAnnotationProcessorTest<YourAnnotationPracessor>_ class. It should return an instance of the annotation processor to use.
3. Add the annotation _@Parameterized_ to the class
4. Create a constructor with parameters _(String description, String resource, String[] errors, String[] warnings)_ which delegates to super constructor.
5. Create a method that provides the parameterized test data. (Annotated with _@Parameterized.Parameters_)
6. Add a test method which delegated to super.test()