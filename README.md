# Holi-Annotation-Processor

> Using the annotation processor mechanism can be a very good possibility to create source code based on Annotated classes and methods or just to ensure the corrrect usage of the annotations. Sadly it's quite complicated to do so, because you have to cope with both java runtime and compile model. This tool is an approach to ease the development of annotation processors by wrapping those java internals inside helper classes.


# How it works

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

# Contributing to holi-annotation-processor

We welcome any kind of suggestions and pull requests. Please notice that TracEE is an integration framework and we will not support
application specific features. We will rather try to enhance our api and empower you to tailor TracEE to your needs.

## Building and developing holi-annotation-processor

The holi-anotation-processor is built using Maven (at least version 3.3.0).
A simple import of the pom in your IDE should get you up and running. To build he holi-annotation-processor on the commandline, just run `mvn clean install`

## Requirements

The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions

- (2017) Tobias Stamann (Holisticon AG)

## Sponsoring

This project is sponsored and supported by [holisticon AG](http://www.holisticon.de/)

# License

This project is released under the revised [BSD License](LICENSE).
