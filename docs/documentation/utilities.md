---
layout: imprintBottom
used_in_navigation: true
menu_name: Utilities
order: 3
---
# Utilities
There are 3 kinds of low level utility classes that are provided with the annotation processor toolkit.

## Type utilities

The *TypeUtils* provide support for

- getting TypeMirrors and TypeElements for Classes and full qualified class names
- checking kinds of TypeMirrors
- getting component type of a TypeMirror of kind array
- comparing and checking of assignability of TypeMirrors of kind array
- comparing of TypeElements and TypeMirrors with generic types

### Handling generic types
It's kind of difficult to get a TypeMirror with a specific generic signature.
This project introduces the *GenericType* class which can be used for comparison.

```java
// creation of a GenericType - it's possible to pass in a TypeMirror,Class or full qualified class name as first parameter

// creates GenericType that represents Map<String,String>
GenericType genericType1 = getTypeUtils().doGenerics().createGenericType(
        Map.class,
        getTypeUtils().doGenerics().createGenericType(String.class),
        getTypeUtils().doGenerics().createGenericType(String.class)
);


// It's also possible to handle wildcards
// creates GenericType that represents typeMirror1Class<*,? extends Class1, ? super xyz.Class2>
GenericType genericType2 = getTypeUtils().doGenerics().createGenericType(
        typeMirror1,
        getTypeUtils().doGenerics().createPureWildcard(),
        getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                getTypeUtils().doGenerics().createGenericType(
                        Class1.class
                )
        ),
        getTypeUtils().doGenerics().createWildcardWithSuperBound(
                getTypeUtils().doGenerics().createGenericType(
                        "xyz.Class2"
                )
        )
);
```


## Element utilities

The *ElementUtils* provide support for

- checking kinds of elements
- casting Elements
- checking Modifiers of elements (f.e. static, final, ...)
- accessing enclosing elements
- accessing enclosed elements

## Messager utilities

The *MessagerUtils* provide support for

- triggering compiler messages
- using placeholders in compiler messages

### Messages and placeholder
The api supports a placeholder mechanism. Placeholder can be used in the message string and are referring the varargs with a zero based index (f.e. ${0} references first vararg).
```java
// f.e. to create an error - see javadoc for further details
getMessager().error(element, "Message with placeholders ${1} before ${2}", "p1", "p2");
```

