---
layout: default
used_in_navigation: true
menu_name: Getting Started
order: 2
---
# Getting Started

# Prerequisites

There are not many preconditions regarding the usage of the annotation processor toolkit.
You just need a JDK 6 or higher to get started.

Nevertheless we recommend you to use Maven for building your annotation processor project.
This page explains what needs to be configured in your Maven configuration to enable the annotation processor toolkit in your projects.

# Build and dependency management
## Dependency mangement

You need to add the following dependencies to your Maven configuration, if you want to develop and test your annotation processors :

```xml
 <dependencies>

     <!-- Needed for developing your annotation processors -->
     <dependency>
         <groupId>de.holisticon.annotationprocessortoolkit</groupId>
         <artifactId>annotationprocessor</artifactId>
         <version>0.4.0</version>
         <scope>compile</compile>
     </dependency>

    <!-- optional, but recommended for testing your annotation processor -->
    <dependency>
        <groupId>de.holisticon.annotationprocessortoolkit</groupId>
        <artifactId>annotationprocessor</artifactId>
        <version>0.4.0</version>
        <scope>test</scope>
    </dependency>

 </dependencies>
```

The testhelper module internally uses com.google.testing.compile compile-testing as a dependency.
To maintain compatibility with Java 6 JDKs version 0.9 is used. All following versions are based on Java 8 JDK.

## Additional configurations

We recommend you to repackage all 3rd party dependencies of your annotation processor in your build artifact.

This should be done for several reasons:

1. To get rid of versioning conflicts between the dependencies of your annotation processor and of the code to be processed.
2. You need to add just one provided scope dependency to the code you want to be processed, since provided dependencies aren't handled transitively by Maven

In Maven, this can be done by using the shade plugin:

```xml
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
```

# Building your annotation processor

Annotation processor are internally build on top of a SPI.
So you just need to do two things to create an annotation processor.

## Create the annotation processor Class‚

Usually your annotation processor class would have to extend the _javax.annotation.processing.AbstractProcessor_ class provided by the JDK.
To enable the annotation processor toolkit support use the _de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor_ instead.

```java
package de.holisticon.example.annotationprocessortoolkit.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Test annotation processor which demonstrates the usage of the annotation processor toolkit.
 */
@SupportedAnnotationTypes({"de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation"})
public class MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor extends AbstractAnnotationProcessor {


    // Overriding the getSupportedAnnotationTypes instead of using the SupportedAnnotationTypes annotation might be an option this is especially useful if you have inheritance
    // private final static Set<String> SUPPORTED_ANNOTATION_TYPES = createSupportedAnnotationSet(MethodWithOneStringParameterAndVoidReturnTypeAnnotation.class);
    // @Override
    // public Set<String> getSupportedAnnotationTypes() {
    //    return SUPPORTED_ANNOTATION_TYPES;
    // }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // ...

        return false;
    }


}

```

## Declare which annotations are processed by the annotation processor

There are two approaches to tell the compiler which annotations are supported by an annnotation processor.

It can either be done by annotation or by overwriting the _getSupportedAnnotationTypes_ method declared by the _javax.annotation.processing.AbstractProcessor_ class.

### By annotation

Supported annotations can be defined by adding the _SupportedAnnotationTypes_ annotation to your annotation processor class:

    @SupportedAnnotationTypes({"de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation"})
    public class MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor extends AbstractAnnotationProcessor {

This is quite elegant but has some drawbacks:

- Supported annotations are declared as a FQN String and therefore not refactoring safe
- Using inheritance with annotation processors won't work, since annotations aren't inherited
- Overwriting of _javax.annotation.processing.AbstractProcessor.getSupportedAnnotationTypes()_ might break configuration by annotation

### By overwriting getSupportedAnnotationTypes

An alternative approach is to overwrite the _getSupportedAnnotationTypes_ method:

```java
private final static Set<String> SUPPORTED_ANNOTATION_TYPES = createSupportedAnnotationSet(Annotation1.class, Annotation2.class);
@Override
public Set<String> getSupportedAnnotationTypes() {
   return SUPPORTED_ANNOTATION_TYPES;
}
```

The logic to configure supported annotations by  the _SupportedAnnotationTypes_ annotation is implemented in _javax.annotation.processing.AbstractProcessor.getSupportedAnnotationTypes()_.
So overwriting of this method might break configuration by annotation.

## Setup annotation processor detection

Since annotation processors are internally detected via a _Service Provider Interface (SPI)_ you need to create a file _javax.annotation.processing.Processor_ in _/src/main/resources_.
The file just contains a list of full qualified annotation processor class names (one per line).
So just add you full qualified class name of your annotation processor to it.

# Applying the annotation processor
Your annotation processor will be automatically detected and applied if it resides in class path during the compilation.
Therefore you should add it as a provided dependency to your projects.


# Further information
Please check our example modules provided in the github.

