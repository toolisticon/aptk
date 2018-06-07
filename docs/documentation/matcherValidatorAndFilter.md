---
layout: imprintBottom
used_in_navigation: true
menu_name: Matchers, Validators and Filters
order: 4
---
# Matchers, Validators and Filters

The toolkit provides some predefined Matchers, Validators and Filters.

Those can be accessed in a static way by using the *CoreMatchers* class.

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

The fluent element validator can be used do validations on Elements.

All of these fluent validators can trigger error or warning messages if validation fails.
Per default a default error message is generated if a validation fails.
The message level and message text can be overwritten by using the fluent api.

#### Example
```java
// creates a fluent validator that issues messages if validation fails
boolean result =
    FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(element))
        .applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
        .applyValidator(CoreMatchers.BY_PARAMETER_TYPE).hasOneOf(wrapToArray(String.class))
        .validateAndIssueMessages();
```


### Fluent Filter

The *FluentElementFilter* class can be used to filter Lists of Elements:

```java
List<? extends Element> result =
    FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
        .getResult();
```

This works great in combination of *Elements.getEnclosedElements*, *ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree*, *ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree*
