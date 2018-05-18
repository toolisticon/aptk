# Annotation-Processor-Toolkit

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.annotationprocessortoolkit/annotationprocessortoolkit-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.annotationprocessortoolkit/annotationprocessortoolkit-parent)
[![Build Status](https://api.travis-ci.org/toolisticon/annotation-processor-toolkit.svg)](https://travis-ci.org/toolisticon/annotation-processor-toolkit)
[![codecov](https://codecov.io/gh/toolisticon/annotation-processor-toolkit/branch/master/graph/badge.svg)](https://codecov.io/gh/toolisticon/annotation-processor-toolkit)

Please check detailed documentation at the projects [github page](https://toolisticon.github.io/annotation-processor-toolkit/)

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
             <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
             <artifactId>annotationprocessor</artifactId>
             <version>0.10.0</version>
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

Then your annotation processor needs to extends the io.toolisticon.annotationprocessor.AbstractAnnotationProcessor to be able to use the utilities offered by this project and to build your annotation processor.

Please check our example provided in the github.

# Examples

## Enhanced utility support
Java itself offers the following utility classes to support you build annotation processors:

- Elements
- Types
- Messager
- Filer

This project provides enhanced support for this classes.
All Utility classes are named like the standard java classes suffixed with Utils.

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

This is are just a few examples of the provided tools. Please check the javadoc for more information.


## Criteria Matching, validation and filtering of Elements with CoreMatchers and fluent API

Matchers can be used to check if an Element matches a specific criteria.
Validation will check for a combination of multiple criteria.
Filters will filter a List of Elements by specific criteria.

Examples:

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




## Template based resource file creation

Resource file creation and source file creation is very simple:

### Sample template file
A rudimentary templating mechanism can be used to create resources.
It provides functionality of dynamic text replacement and for and if control blocks.

    !<if textArray != null>
        !<for text:textArray>
            Dynamic text: !{text}<br />
        !</for>
    !</if>

### Sample code

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
