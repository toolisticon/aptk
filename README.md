# APTK - The Annotation Processor Toolkit

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.aptk/aptk-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.aptk/aptk-parent)
![Build Status](https://github.com/toolisticon/aptk/workflows/default/badge.svg)
[![codecov](https://codecov.io/gh/toolisticon/aptk/branch/master/graph/badge.svg)](https://codecov.io/gh/toolisticon/aptk)

# Why you should use this project?

Nowadays, no one could imagine Java development without annotations. They allow you to provide meta-data in your source
code which can either be processed at runtime via reflection or at compile time by using annotation processors.

Annotation processors allow you

- to validate if your annotations are used correctly
- to generate source and resource files or even classes

at compilation time.

Validation by using annotation processors can become quite handy, if there are some constraints related to the usage of
annotations. Without validation by an annotation processor misuse of the annotation could only be detected on runtime.
But in a lot of cases this could already be evaluated at compile time by using annotation processors which could trigger
a compilation error in case of a constraint violation. Additionally, annotation processor driven code or resource file
generation can also be very useful.

Unfortunately it's quite uncomfortable to develop and test annotation processors. First problem is that you have to cope
with both java compile time and run time model, which can be very tricky at the beginning. Another problem is that the
tools offered by java only offer some basic support for development. This project supports you by providing utilities
that allow you to develop annotation processors in a more comfortable way. It also reduces the complexity of handling
compile time and runtime model by shading common pitfalls behind it's api.

# Features

- provides a processor for generating wrapper classes for accessing annotation attributes
- provides wrappers for _Elements_ and _TypeMirror_ that provide a lot of useful utility functions
- provides support for Class conversion from runtime to compile time model (Class / FQN to Element and TypeMirror)
- provides support for accessing the compile time element tree
- provides generic Element based filters, validator and matchers
- provides fluent element validation and filtering api
- provides support for template based creation of java source and resource files
- compatible with all java versions >=8 (dropped java 7 compatibility with version 0.20.0)
- higher Java level features like modules, records and sealed classes are accessible (internally handled via reflection)

# Getting started

Best way to start is to use the [APTK maven archetype](https://github.com/toolisticon/annotationprocessor-archetype) to create a basic project based on the APTK stack. The generated code contains some example code demonstrating how the APTK framework can be used.
It's generally a good starting point for your annotation processor project or can be used as a sandbox to get in touch with the framework.

# How does it work?

This project provides the abstract base class _io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor_
which extends the AbstractProcessor class provided by java. Your annotation processor needs to extends this class to be
able to use the utilities offered by this project and to build your annotation processor.

## Manually init ToolingProvider if you don't use the AbstractAnnotationProcessor
Nevertheless, you can even use this library if your processor doesn't extend the _io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor_. You need to initialize the _ToolingProvider_ manually in your processor - best place to do this is either in your processors _init_ or _process_ method:

```java
ToolingProvider.setTooling(processingEnv);
```

## Delivering your processor
In general, you should consider to have as few external dependencies as possible used by your processor.
It's a good approach to use the maven shade plugin to repackage and embed the annotation-processor-toolkit and all other 3rd party dependencies into your annotation processor artifact. This can be done by adding the following to your annotation processors pom.xml:

```xml

<dependencies>

    <dependency>
        <groupId>io.toolisticon.aptk</groupId>
        <artifactId>aptk-tools</artifactId>
        <version>0.22.5</version>
    </dependency>

    <!-- recommended for testing your annotation processor -->
    <dependency>
        <groupId>io.toolisticon.cute</groupId>
        <artifactId>cute</artifactId>
        <version>0.12.1</version>
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

                    <!-- remove shaded dependencies from pom.xml -->
                    <createDependencyReducedPom>true</createDependencyReducedPom>

                    <!-- need to relocate used 3rd party dependencies and their transitive dependencies -->
                    <relocations>
                        <relocation>
                            <pattern>io.toolisticon.aptkio.toolisticon.aptk</pattern>
                            <shadedPattern>
                                your.projects.base.package._3rdparty.io.toolisticon.aptk
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

## Annotation Wrapper

Reading attribute values can be very complicated if it comes to annotation type or Class based attributes. In this case
you are often forced to read the attribute values via the _AnnotationMirror_ api. Additionally, you usually have to create
some kind of class to store those annotation configurations of the annotation.

The APTK provides an annotation processor that generates wrapper classes that allow you to access the annotation attribiute like if you are
accessing the annotation directly. Only difference is that Class type based attributes will be accessible as FQN String,
TypeMirror or TypeMirrorWrapper. Annotation type based attributes will be also wrapped to ease access. 

A small example: 

Annotation :
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrettyExample {
    String aStringBasedValue();
    Class<?> typeBasedAttribute();
}
```

can be accessed the following way:
```java
PrettyExampleWrapper wrapper = PrettyExampleWrapper.wrap(element);

// access annotated element
Element annotatedElement = wrapper._annotatedElement();

// access annotation mirror
AnnotationMirror annotationMirror = wrapper._annotationMirror();

// read type based attributes
TypeMirror typeMirror = wrapper.typeBasedAttributeAsTypeMirror();
TypeMirrorWrapper typeMirrorWrapper = wrapper.typeBasedAttributeAsTypeMirrorWrapper();
String fqn = wrapper.typeBasedAttributeAsFqn();

```
Annotation based attributes will be accessible via their AnnotationWrappers as well.

Please check [annotation wrapper processor](annotationwrapper) for further information.

## Element Wrappers

Element wrappers are quite helpful by extending the Element API by enhanced utility functions, which for example help to 
navigate through the element tree or by providing Element validation support.

Some examples:

```java

// validation - lambda style
Element element = null;
ElementWrapper.wrap(element).validate()
    .asError().withCustomMessage("Annotation must be placed on static inner class with public or protected modifier")
    .check( ElementWrapper::isClass)
    .and(e -> e.hasModifiers(Modifier.STATIC) && (e.hasModifiers(Modifier.PUBLIC) || e.hasModifiers(Modifier.PROTECTED)))
    .validate();

// same validation APTK style - with generic compiler messages
ElementWrapper.wrap(element).validateWithFluentElementValidator()
    .is(AptkCoreMatchers.IS_CLASS)
    .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.STATIC)
    .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasOneOf(Modifier.PUBLIC, Modifier.PROTECTED)
    .validateAndIssueMessages();

// Navigation / Filtering
List<TypeElementWrapper> allStaticInnerClasses = ElementWrapper.wrap(element).getAllEnclosingElements().stream()
    .filter(ElementWrapper::isClass)
    .filter(e -> e.hasModifiers(Modifier.STATIC) && (e.hasModifiers(Modifier.PUBLIC) || e.hasModifiers(Modifier.PROTECTED)))
    .map(ElementWrapper::toTypeElement)
    .collect(Collectors.toList());

// getting methods of TypeElement
TypeElement typeElement = null;
Optional<ExecutableElementWrapper> method = TypeElementWrapper.wrap(typeElement).getMethod("methodName", String.class, Long.class);

// ...


```                                                

## TypeMirror wrapper
There is a wrapper for TypeMirrors as well. It's called _TypeMirrorWrapper_ and provides a lot of usefull tools like checking Assignability.
Usage is similar to the Element wrapper:

```java
TypeMirrorWrapper.wrap(typeMirror);
```


## Enhanced utility support

Java itself provides some tools to support you to build annotation processors. This framework provides some utility
classes to add some useful features not covered by these tools:

- Elements : _ElementUtils_ provides support to navigate through the Element tree
- Types : _TypeUtils_ provides support to cope with type in java compile time model
- Messager : _MessagerUtils_ provides support to issue messages during compilation
- Filer : _FilerUtils_ provides support to access or write java source or resource files

There are some more helpful utility classes:

- _AnnotationUtils_ : provides support for reading annotation attribute values
- _AnnotationValueUtils_ : provides support for handling _AnnotationValue_;
- _InterfaceUtils_ : provides support for handling generic interfaces and superclasses, for example to determine concrete types of type variables in super classes or parent interfaces (still experimental)

Example:

```java
// Check if TypeMirror is Array
boolean isArray=TypeUtils.CheckTypeKind.isArray(aTypeMirror);

// get TypeElement or TypeMirrors easily
TypeElement typeElement1=TypeUtils.TypeRetrieval.getTypeElement("fqn.name.of.Clazz");
TypeElement typeElement2=TypeUtils.TypeRetrieval.getTypeElement(Clazz.class);
TypeMirror typeMirror1=TypeUtils.TypeRetrieval.getTypeMirror("fqn.name.of.Clazz");
TypeMirror typeMirror2=TypeUtils.TypeRetrieval.getTypeMirror(Clazz.class);

boolean checkAssignability=TypeUtils.TypeComparison.isAssignableTo(typeMirror1,typeMirror2);

// get all enclosed elements annotated with Deprecated annotation
List<?extends Element> enclosedElements=ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element,Deprecated.class);
```

These are just a few examples of the provided tools. Please check the javadoc for more information.

## Characteristic matching, validation and filtering of Elements with core matchers and fluent API

The framework provides a set of core matchers that can be used to check if an Element matches a specific characteristic.

Those core matchers can also be used for validation - validators allow you to check if an element matches none, one, at
least one or all of the passed characteristics.

Additionally, the core matchers can be used to filter a List of Elements by specific characteristics.

The framework provides a _FluentElementValidator_ and a _FluentElementFilter_ class that allow you to combine multiple
filters and validations by providing a simple and powerful fluent api.

Please check following examples:

```java
List<Element> elements=new ArrayList<Element>();

// validator already will print output so additional actions are not necessary
FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castToTypeElement(element))
.applyValidator(AptkCoreMatchers.IS_ASSIGNABLE_TO).hasOneOf(SpecificInterface.class)
.validateAndIssueMessages();

// Matcher checks for a single criteria
boolean isPublic=AptkCoreMatchers.BY_MODIFIER.getMatcher().checkForMatchingCharacteristic(element,Modifier.PUBLIC);

// Validator checks for multiple criteria : none of, one of, at least one of or all of
boolean isPublicAndStatic=AptkCoreMatchers.BY_MODIFIER.getValidator().hasAllOf(element,Modifier.PUBLIC,Modifier.STATIC);

// Filter checks for multiple criteria and returns a List that contains all matching elements
List<Element> isPublicAndStaticElements=AptkCoreMatchers.BY_MODIFIER.getFilter().filterByAllOf(elements,Modifier.PUBLIC,Modifier.STATIC);

// Just validates without sending messages
boolean isPublicAndStatic2=FluentElementValidator.createFluentElementValidator(element)
.applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC,Modifier.STATIC)
.justValidate();

// Validate and send messages in case of failing validation
FluentElementValidator.createFluentElementValidator(element)
.applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC,Modifier.STATIC)
.validateAndIssueMessages();


// Filters list by criteria : returns all method Elements that are public and static
List<ExecutableElement> filteredElements=FluentElementFilter.createFluentElementFilter(elements)
.applyFilter(AptkCoreMatchers.IS_METHOD)
.applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC,Modifier.STATIC)
.getResult();
```

## Template based java source and resource file creation

Template based java source Resource file creation and source file creation is very simple:

### Sample template file

The framework provides a rudimentary templating mechanism which can be used to create resource and java source files. It
supports dynamic text replacement and for and if control blocks.

    !{if textArray != null}
        !{for text:textArray}
            Dynamic text: ${text}<br />
        !{/for}
    !{/if}

### Sample code : Resource file creation

```java
String[]textArray={"A","B","C"};

// create Model
Map<String, Object> model=new HashMap<String, Object>();
model.put("textArray",textArray);

final String package="io.toolisticon.example";
final String fileName="generatedExample.txt";

try{
    // template is loaded resource
    SimpleResourceWriter resourceWriter=FilerUtils.createResource(StandardLocation.CLASS_OUTPUT,package,fileName);
    resourceWriter.writeTemplate("example.tpl",model);
    resourceWriter.close();
}catch(IOException e){
    MessagerUtils.error(null,"Example file creation failed for package '${0}' and filename '${1}'",package,fileName);
}
```

Please check [template engine](templating) for further information.

## Alternative way for creating source and resource files

You don't have to use the builtin template library. 
You can also use any other template library or even java-poet or kotlin-poet for source and resource file creation.
The FilerUtils utility class also provides a way to write Strings to Source and Resource files.


# Projects using this toolkit library

- [bean-builder](https://github.com/toolisticon/bean-builder) : An annotation processor to generate fluent instance
  builder classes for bean classes
- [SPIAP](https://github.com/toolisticon/SPI-Annotation-Processor) : An annotation processor that helps you to generate
  SPI configuration files and service locator classes
- [FluApiGen](https://github.com/toolisticon/FluApiGen) : An annotation processor that generates fluent api implementation based on annotated interfaces
- APTK itself provides some annotation processors based on the APTK for generating wrappers for annotations or compiler messages 

# Useful links

## Compile time testing of annotation processors

- [Toolisticon CUTE](https://github.com/toolisticon/cute) : A simple compile testing framework that allows you to test
  annotation processors. It was extracted from this project is a great help to unit test your annotation processor code. It supports both unit and black box testing of the processors code.
- [google compile-testing](https://github.com/google/compile-testing) : Another compile testing framework which was used
  in the past by this framework. It has some flaws like missing compatibility with different Java versions, is binding a
  lot of common 3rd party libraries, and has almost no documentation.

# Contributing

We welcome any kind of suggestions and pull requests.

## Building and developing annotation-processor-toolkit

The annotation-processor-toolkit is built using Maven (at least version 3.0.0).

To build the annotation-processor-toolkit on the commandline, just run `mvn` or `mvn clean install`

## Requirements

The likelihood of a pull request being accepted rises with the following properties:

- You have used a feature branch.
- You have included tests that demonstrate the functionality added or fixed.
- You adhered to
  the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions

- (2017) Tobias Stamann (Holisticon AG)

# License

This project is released under the revised [MIT License](LICENSE).
