---
layout: imprintBottom
used_in_navigation: true
menu_name: Basics
order: 1
---
# About the examples

The examples describe the usage of the tools from inside your annotation processor that extends the *de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor* class.

Nevertheless all tools can be accessed throughout the application:

```java

// Get TypeUtils which provides utilities for working with TypeElements and Type Mirrors
// (TypeElement and TypeMirror retrieval and comparision)
TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnvironment);

// ElementUtils can be used in a static way. f.e.
ElementUtils.CheckModifierOfElement.hasFinalModifier(element);

// Get MessagerUtils which provides utilities for triggering compiler messages
MessagerUtils messagerUtils = MessagerUtils.getMessagerUtils(processingEnvironment);


```

# General remarks
There are some pitfalls when you are working with TypeMirrors and TypeElements.

- You can use a Class to retrieve the TypeMirror or TypeElement. But this is just working if used Class is already compiled (f.e. by using Classes declared in dependencies). For Classes under copilation you must use the full qualified classname instead.
- For some types no TypeElement exists. In general it affects all types which don't extend java.lang.Object - this corresponds to all primitive and array types.
- Retrieval of a TypeMirror that has a specific generic type signature is very difficult. If you want to check equality or assignability of generic types use the generic tools provided by this project.
