---
layout: imprintBottom
used_in_navigation: true
menu_name: Matchers, Validators and Filters
order: 4
---
# Matchers, Validators and Filters

The toolkit provides some predefined Matchers, Validators and Filters.

Those matchers can be accessed in a static way by using the *Matchers*, *Validators* and *Filters* classes.

Currently processing by the following criteria is supported:

- by annotation
- by element kind
- by modifier
- by name
- by name with regular expression
- by parameter (Class[])
- by parameter (full qualified names = String[])
- by raw type
- by generic type


### Matcher

Matcher allow you to compare if an Elements has a specific characteristic.

### Validator

Validators allow you to pass in multiple criteria and to check if

- one
- at least one
- all
- none

criteria are fulfilled.

### Filter

Filter can be used to filter Lists of Elements by using a specific validator.

## Fluent APIs

### Fluent Validators

There are currently 3 different fluent validators:

- FluentExecutableElementValidator
- FluentModifierElementValidator
- FluentTypeElementValidator

All of these fluent validators will trigger error or warning messages if validation fails.
Per default a default error message is generated if a validation fails.
The message level and message text can be overwritten by using the fluent api.

#### Example
```java
// usage of FluentExecutableElementValidator
boolean result = new FluentExecutableElementValidator(getFrameworkToolWrapper(),element)
    .isMethod()
    .warning().setCustomMessage("MESSAGE WITH ${0}HOLDER", "PLACE").hasName("methodName")
    .hasNonVoidReturnType()
    .hasModifiers(Modifier.STATIC,Modifier.PUBLIC)
    .hasParameters(String.class, Long.class)
    .getValidationResult();
```


### Fluent Filter

The *FluentElementFilter* class can be used to filter Lists of Elements:

```java
List<? extends Element> result =
    FluentElementFilter.createFluentFilter(element.getEnclosedElements())
        .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
        .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter")
        .getResult();
```

This works great in combination of *Elements.getEnclosedElements*, *ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree*, *ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree*
