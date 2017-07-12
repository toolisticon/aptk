---
layout: imprintBottom
used_in_navigation: true
menu_name: Element utilities
order: 2
---
# Element utilities

Since there are no dependencies on the ProcessingEnvironment the ElementUtilities can be accessed throughout the annotation processor in a static way.


## Check kind of element

It's quite easy to check the kind of an Element:

```java
boolean result = ElementUtils.CheckKindOfElement.isEnum(element);
boolean result = ElementUtils.CheckKindOfElement.isConstructor(element);
boolean result = ElementUtils.CheckKindOfElement.isClass(element);
boolean result = ElementUtils.CheckKindOfElement.isField(element);
boolean result = ElementUtils.CheckKindOfElement.isInterface(element);
boolean result = ElementUtils.CheckKindOfElement.isParameter(element);
boolean result = ElementUtils.CheckKindOfElement.isClass(element);
boolean result = ElementUtils.CheckKindOfElement.isOfKind(element, kind);
```

## Check type and cast of Elements

There's support for checking the type of an Element and to cast it:


```java
// convenience methods to check the type of an element
ElementUtils.CastElement.isExecutableElement(element);
ElementUtils.CastElement.isVariableElement(element);
ElementUtils.CastElement.isTypeElement(element);

// convenience methods to cast to a specific Element kind
ExecutableElement castedElement = ElementUtils.CastElement.castToExecutableElement(element);
VariableElement castedElement = ElementUtils.CastElement.castToVariableElement(element);
TypeElement castedElement = ElementUtils.CastElement.castToTypeElement(element);

// convenience methods to cast the elements by kind
ExecutableElement castedElement = ElementUtils.CastElement.castMethod(element);
ExecutableElement castedElement = ElementUtils.CastElement.castConstructor(element);
VariableElement castedElement = ElementUtils.CastElement.castField(element);
VariableElement castedElement = ElementUtils.CastElement.castParameter(element);
TypeElement castedElement = ElementUtils.CastElement.castClass(element);
TypeElement castedElement = ElementUtils.CastElement.castEnum(element);
TypeElement castedElement = ElementUtils.CastElement.castInterface(element);

```