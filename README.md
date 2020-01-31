# Annotation-Processor-Toolkit (APTK)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.annotationprocessortoolkit/annotationprocessortoolkit-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.annotationprocessortoolkit/annotationprocessortoolkit-parent)
[![Build Status](https://api.travis-ci.org/tobiasstamann/annotation-processor-toolkit.svg)](https://travis-ci.org/tobiasstamann/annotation-processor-toolkit)
[![codecov](https://codecov.io/gh/tobiasstamann/annotation-processor-toolkit/branch/master/graph/badge.svg)](https://codecov.io/gh/tobiasstamann/annotation-processor-toolkit)

# Why you should use this project?

Nowadays no one could imagine Java development without annotations.
They allow you to provide meta-data in your source code which can either be processed at runtime via reflection or at compile time by using annotation processors.

Annotation processors allow you

- to validate if your annotations are used correctly 
- to generate source and resource files or even classes

at compilation time.

Validation by using annotation processors can become quite handy, if there are some constraints related to the usage of annotations. 
Without validation by an annotation processor misuse of the annotation could only be detected on runtime. 
But in a lot of cases this could already be evaluated at compile time by using annotation processors which could trigger a compilation error in case of a constraint violation.
Additionally, annotation processor driven code or resource file generation can also be very useful.

Unfortunately it's quite uncomfortable to develop and test annotation processors.
First problem is that you have to cope with both java compile time and run time model, which can be very tricky at the beginning.
Another problem is that the tools offered by java only offer some basic support for development.
This project supports you by providing utilities that allow you to develop annotation processors in a more comfortable way.
It also reduces the complexity of handling compile time and runtime model by shading common pitfalls behind it's api.

# Features
- provides support for Class conversion from runtime to compile time model (Class / FQN to Element and TypeMirror)
- provides support for accessing the compile time element tree
- provides generic Element based filters, validator and matchers
- provides fluent element validation and filtering api
- provides support for template based creation of java source and resource files
- compatible with all java versions >=6 (java >=9 compatibility since version 0.12.0)

# How does it work?

This project provides the abstract base class _io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor_ which extends the AbstractProcessor class provided by java. 
Your annotation processor needs to extends this class to be able to use the utilities offered by this project and to build your annotation processor.

Since your annotation processor later mostly will be bound as a provided dependency you should use the maven shade plugin to embed the annotation-processor-toolkit and all other 3rd party dependency classes into your annotation processor artifact.
This can be done by adding the following to your annotation processors pom.xml:

```xml
     <dependencies>

         <dependency>
             <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
             <artifactId>annotationprocessor</artifactId>
             <version>${aptk.currentVersion}</version>
         </dependency>

        <!-- recommended for testing your annotation processor -->
        <dependency>
            <groupId>io.toolisticon.compiletesting</groupId>
            <artifactId>compiletesting</artifactId>
            <version>${compileTesting.currentVersion}</version>
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
                                     <pattern>io.toolisticon.annotationprocessortoolkit</pattern>
                                     <shadedPattern>
                                         your.projects.base.package._3rdparty.io.toolisticon.annotationprocessortoolkit
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

Please check our example provided in the github.

# Examples

## Enhanced utility support
Java itself provides some tools to support you to build annotation processors. 
This framework provides some utility classes to add some useful features not covered by these tools:

- Elements : _ElementUtils_ provides support to navigate through the Element tree
- Types : _TypeUtils_ provides support to cope with type in java compile time model
- Messager : _MessagerUtils_ provides support to issue messages during compilation
- Filer : _FilerUtils_ provides support to access or write java source or resource files 

Example:
```java
     // Check if TypeMirror is Array
     boolean isArray = TypeUtils.CheckTypeKind.isArray(aTypeMirror);

     // get TypeElement or TypeMirrors easily
     TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement("fqn.name.of.Clazz");
     TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(Clazz.class);
     TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror("fqn.name.of.Clazz");
     TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(Clazz.class);

     boolean checkAssignability = TypeUtils.TypeComparison.isAssignableTo(typeMirror1, typeMirror2);

     // get all enclosed elements annotated with Deprecated annotation
     List<? extends Element> enclosedElements = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element,Deprecated.class);
```

These are just a few examples of the provided tools. Please check the javadoc for more information.


## Characteristic matching, validation and filtering of Elements with CoreMatchers and fluent API

The framework provides a set of CoreMatchers that can be used to check if an Element matches a specific characteristic.

Those CoreMatchers can also be used for validation - validators allow you to check if an element matches none, one, at least one or all of the passed characeristics.  

Additionally the CoreMatchers can be used to filter a List of Elements by specific characeristics.

The framework provides a _FluentElementValidator_ and a _FluentElementFilter_ class that allow you to combine multiple filters and validations by providing a simple and powerfull fluent api.

Please check following examples:
```java
    List<Element> elements = new ArrayList<Element>();

    // validator already will print output so additional actions are not necessary
    FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castToTypeElement(element))
            .applyValidator(CoreMatchers.IS_ASSIGNABLE_TO).hasOneOf(SpecificInterface.class)
            .validateAndIssueMessages();

    // Matcher checks for a single criteria
    boolean isPublic = CoreMatchers.BY_MODIFIER.getMatcher().checkForMatchingCharacteristic(element, Modifier.PUBLIC);

    // Validator checks for multiple criteria : none of, one of, at least one of or all of
    boolean isPublicAndStatic = CoreMatchers.BY_MODIFIER.getValidator().hasAllOf(element, Modifier.PUBLIC,Modifier.STATIC);

    // Filter checks for multiple criteria and returns a List that contains all matching elements
    List<Element> isPublicAndStaticElements = CoreMatchers.BY_MODIFIER.getFilter().filterByAllOf(elements, Modifier.PUBLIC,Modifier.STATIC);

    // Just validates without sending messages
    boolean isPublicAndStatic2 = FluentElementValidator.createFluentElementValidator(element)
            .applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC,Modifier.STATIC)
            .justValidate();

    // Validate and send messages in case of failing validation
    FluentElementValidator.createFluentElementValidator(element)
            .applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC,Modifier.STATIC)
            .validateAndIssueMessages();


    // Filters list by criteria : returns all method Elements that are public and static
    List<ExecutableElement> filteredElements = FluentElementFilter.createFluentElementFilter(elements)
            .applyFilter(CoreMatchers.IS_METHOD)
            .applyFilter(CoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC,Modifier.STATIC)
            .getResult();
```

## Template based java source and resource file creation

Template based java source Resource file creation and source file creation is very simple:

### Sample template file
The framework provides a rudimentary templating mechanism which can be used to create resource and java source files.
It supports dynamic text replacement and for and if control blocks.

    !{if textArray != null}
        !{for text:textArray}
            Dynamic text: ${text}<br />
        !{/for}
    !{/if}

### Sample code : Resource file creation

```java
    String[] textArray = {"A","B","C"};

    // create Model
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("textArray", textArray);

    final String package = "io.toolisticon.example";
    final String fileName = "generatedExample.txt";

    try {
        // template is loaded resource
        SimpleResourceWriter resourceWriter = FilerUtils.createResource(StandardLocation.CLASS_OUTPUT, package, fileName);
        resourceWriter.writeTemplate("example.tpl", model);
        resourceWriter.close();
    } catch (IOException e) {
        MessagerUtils.error(null, "Example file creation failed for package '${0}' and filename '${1}'", package, fileName);
    }
```


# Projects using this toolkit library

- [bean-builder](https://github.com/toolisticon/bean-builder) : An annotation processor to generate fluent instance builder classes for bean classes
- [SPIAP](https://github.com/toolisticon/SPI-Annotation-Processor) : An annotation processor that helps you to generate SPI configuration files and service locator classes

# Useful links

## Compile time testing of annotation processors

- [toolisticon compile-testing](https://github.com/toolisticon/compile-testing) : A simple compile testing framework that allows you to test annotation processors. It is used in this project as well.
- [google compile-testing](https://github.com/google/compile-testing) : Another compile testing framework which was used in the past by this framework. It has some flaws like missing compatibility with different Java versions, is binding a lot of common 3rd party libraries, and has almost no documentation

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


# License

This project is released under the revised [MIT License](LICENSE).
