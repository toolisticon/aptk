# Holi-Annotation-Processor-Toolkit

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.holisticon.annotationprocessortoolkit/annotationprocessor-toolkit-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.holisticon.annotationprocessortoolkit/annotationprocessor-toolkit-parent)
[![Build Status](https://api.travis-ci.org/holisticon/annotation-processor-toolkit.svg)](https://travis-ci.org/holisticon/annotation-processor-toolkit)
[![Coverage Status](https://coveralls.io/repos/holisticon/annotation-processor-toolkit/badge.svg?branch=master&service=github)](https://coveralls.io/holisticon/holisticon/annotation-processor-toolkit?branch=master)


> Using the annotation processor mechanism can be a very good possibility to create source code based on annotated classes and methods or just to ensure the correct usage of the annotations in your source code. Sadly it's quite complicated to do so, because you have to cope with both java runtime and compile time model. This toolkit is an approach to ease the development of annotation processors by wrapping those java internals inside mostly fluent and immutable helper classes.

# Example

We will show you the difference between developing compile time validations with java tools and our project to detect incorrect usage of an annotation.
We have an annotation processor that processes the TestAnnotation which is applicable on Classes which must be public, not abstract and must implement the SomeInterface interface.

At first here's what the code looks like when you are using the java api:

    // example with java api
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // ...

        if(!typeElement.getModifiers().contains(Modifier.PUBLIC)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,"Type must be public",typeElement);
        }

        if(typeElement.getModifiers().contains(Modifier.ABSTRACT)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,"Type must not be abstract",typeElement);
        }

        if(!processingEnv.getTypeUtils().isAssignable(typeElement,
                processingEnv.getElementUtils().getTypeElement(SomeInterface.class.getCanonicalName()))) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,"Type must be assignable to SomeInterface",typeElement);
        }

        // ...

    }

Now take a look at the implementation done with the holi-annotation-processor-toolkit

    // example with holi-annotation-processor-toolkit triggered from your
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // ...

        this.getFluentTypeValidator(typeElement)
            .hasModifiers(Modifier.PUBLIC)
            .hasNotModifiers(Modifier.ABSTRACT)
            .isAssignableTo(SomeInterface.class)
            .getValidationResult()

        // ...

    }

That's it. In case of a failing validation, a default error message for the failed kind of validation will be printed to the compiler.
As you can see, it's far more readable and better to understand.

But now you can say what about print a custom message as a warning. That's also possible:


    // example with holi-annotation-processor-toolkit triggered from your
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // ...

        this.getFluentTypeValidator(element)
            .setCustomMessage(Diagnostic.Kind.WARNING, "The class annotated with annotation ${0} must be public.", TestAnnotation.class.getCanonicalName()).hasModifiers(Modifier.PUBLIC)
            .setCustomMessage(Diagnostic.Kind.WARNING, "The class annotated with annotation ${0} must not be abstract.", TestAnnotation.class.getCanonicalName()).hasNotModifiers(Modifier.ABSTRACT)
            .setCustomMessage(Diagnostic.Kind.ERROR, "The class annotated with annotation ${0} must be assignable to SomeInterface", TestAnnotation.class.getCanonicalName()).isAssignableTo(SomeInterface.class)
            .getValidationResult();

        // ...

    }

# How does it work?

This project offers an abstract base class which extends the AbstractProcessor class offered by the java framework.
This class provides support for validating different kinds of Elements in a fluent way and offers you helper functions to do some filtering.

So all you need to do is to add the following maven dependency to your project:

     <dependency>
         <groupId>de.holisticon.holiannotationprocessor</groupId>
         <artifactId>holi-annotationprocessor</artifactId>
         <version>0.1.0-SNAPSHOT</version>
     </dependency>

Then your annotation processor needs to extends the de.holisticon.annotationprocessor.AbstractAnnotationProcessor to be able to use the utilities offered by this project and to build your annotation processor.

You can find a small example project for an annotation processor here:

# Contributing to holi-annotation-processor-toolkit

We welcome any kind of suggestions and pull requests.

## Building and developing holi-annotation-processor-toolkit

The holi-annotation-processor-toolkit is built using Maven (at least version 3.3.0).
A simple import of the pom in your IDE should get you up and running. To build the holi-annotation-processor-toolkit on the commandline, just run `mvn` or `mvn clean install`

## Requirements

The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions

- (2017) Tobias Stamann (Holisticon AG)

## Sponsoring

This project is sponsored and supported by [holisticon AG](http://www.holisticon.de/)

![Holisticon AG](holisticon-logo.png)

# License

This project is released under the revised [BSD License](LICENSE).
