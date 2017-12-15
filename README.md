# Annotation-Processor-Toolkit

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.holisticon.annotationprocessortoolkit/annotationprocessor-toolkit-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.holisticon.annotationprocessortoolkit/annotationprocessor-toolkit-parent)
[![Build Status](https://api.travis-ci.org/holisticon/annotation-processor-toolkit.svg)](https://travis-ci.org/holisticon/annotation-processor-toolkit)
[![codecov](https://codecov.io/gh/holisticon/annotation-processor-toolkit/branch/master/graph/badge.svg)](https://codecov.io/gh/holisticon/annotation-processor-toolkit)

Please check detailed documentation at the projects [github page](https://holisticon.github.io/annotation-processor-toolkit/)

# Why you should use this project?

Nowadays one could not imagine Java development without annotions.
They allow you to provide meta-data in your source code which can either be processed at run time via reflection or at compile time by using annotation processors.

Annotation processors allow you to validate if your annotations are used correctly and allow you to generate resource files or even classes.

Validation by annotation processors can become quite handy if there are any constraints related with your annotations. Without validation by an annotation processor misuse of the annotation can only be detected on runtime. By using an annotation processor this can be validated at compile time and may trigger a compile error.
Code or resource generation with annotations can also be very useful.

Sadly it's quite uncomfortable to develop and test annotation processors.
First problem is that you have to cope with both both java compile time and run time model, which can be very tricky at the beginning.
Another problem is that the tools offered by the JDK only offer some basic support for development.
This project supports you by offering utilities that allow you to develop annotation processors in a more comfortable way.
It also reduces the complexity of handling compile time and run time model by shading common pitfalls behind it's api.'
Additionally it introduces a common approach how those annotation processors can be tested.

# Features
- provides support for Class conversion from runtime to compile time model (Class / FQN to Element and TypeMirror)
- provides support for accessing the compile time element tree
- provides generic Element based filters, validator and matchers
- provides fluent element validation and filtering api
- provides basic support for creation of resources
- provides support for setting up unit and integration (compile time) tests

# How does it work?

This project offers an abstract base class which extends the AbstractProcessor class offered by the java framework.
This class provides support for validating different kinds of Elements in a fluent way and offers you helper functions to do some filtering.

Since your annotation processor later mostly will be bound as a provided dependency you should use the maven shade plugin to embed the annotation-processor-toolkit and all other 3rd party dependency classes into your annotation processor artifact.
This can be done by adding the following to your annotation processors pom.xml:

     <dependencies>

         <dependency>
             <groupId>de.holisticon.annotationprocessortoolkit</groupId>
             <artifactId>annotationprocessor</artifactId>
             <version>0.6.0</version>
         </dependency>

        <!-- recommended for testing your annotation processor -->
        <dependency>
            <groupId>com.google.testing.compile</groupId>
            <artifactId>compile-testing</artifactId>
            <!-- use version 0.9 if you need java 6 compatibility - later versions are based on java 8 -->
            <version>0.10</version>
            <scope>test</scope>
        </dependency>

     </dependencies>

     <build>
         <plugins>

             <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-shade-plugin</artifactId>
                 <version>2.4.3</version>
                 <executions>
                     <execution>
                         <phase>package</phase>
                         <goals>
                             <goal>shade</goal>
                         </goals>
                         <configuration>
                             <!-- need to relocate used 3rd party dependencies and their transitive dependencies -->
                             <relocations>
                                 <relocation>
                                     <pattern>de.holisticon.annotationprocessortoolkit</pattern>
                                     <shadedPattern>
                                         your.projects.base.package._3rdparty.de.holisticon.annotationprocessortoolkit
                                     </shadedPattern>
                                 </relocation>
                             </relocations>
                         </configuration>
                     </execution>
                 </executions>
             </plugin>

         </plugins>
     </build>

Then your annotation processor needs to extends the de.holisticon.annotationprocessor.AbstractAnnotationProcessor to be able to use the utilities offered by this project and to build your annotation processor.

Please check our example provided in the github.

# Projects using this toolkit library

- [SPIAP](https://github.com/toolisticon/SPI-Annotation-Processor) : An annotation processor that helps you to generate SPI configuration files and service locator classes


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

![Holisticon AG](/docs/assets/img/sponsors/holisticon-logo.png)

# License

This project is released under the revised [BSD License](LICENSE).
