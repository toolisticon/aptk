---
layout: default
used_in_navigation: true
menu_name: Home
isSection: false
isIndex: true
order: 1
---

# Why this project might help you

Nowadays one could not imagine Java development without annotions.
They allow you to provide meta-data in your source code which can either be processed at run time via reflection or at compile time by using annotation processors.

Annotation processors allow you to validate if your annotations are used correctly and allow you to generate resource files or even classes.

Validation by annotation processors can become quite handy if there are any constraints related with your annotations. Without validation by an annotation processor misuse of the annotation can only be detected on runtime. By using an annotation processor this can be validated at compile time and may trigger a compile error.
Code or resource generation with annotations can also be very useful.

Sadly it's quite uncomfortable to develop and test annotation processors.
First problem is that you have to cope with both both java compile time and run time model, which can be very tricky at the beginning.
Another problem is that the tools offered by the JDK only offer some basic support for development.

This project supports you by offering utilities that allow you to develop annotation processors in a more comfortable way.
It also reduces the complexity of handling compile time and run time model by shading common pitfalls behind it's api.
Additionally it introduces a common approach for testing your annotation processors.

# Features
- provides support for Class conversion from runtime to compile time model (Class / FQN to Element and TypeMirror)
- provides support for accessing the compile time element tree
- provides generic Element based filters, validator and matchers
- provides fluent element validation and filtering api
- provides basic support for creation of resources
- provides support for setting up unit and integration (compile time) tests


# Contributing

We welcome any kind of suggestions and pull requests.

## Building and developing annotation-processor-toolkit

The annotation-processor-toolkit is built using Maven (at least version 3.0.0).
A simple import of the pom in your IDE should get you up and running. To build the annotation-processor-toolkit on the commandline, just run `mvn` or `mvn clean install`

## Requirements

The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions

- (2017) Tobias Stamann (Holisticon AG)

## Sponsoring

This project is sponsored and supported by [holisticon AG](http://www.holisticon.de/)

![Holisticon AG]({{ '/assets/img/sponsors/holisticon-logo.png' | relative_url }})

# License

This project is released under the revised [BSD License](LICENSE).