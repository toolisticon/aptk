---
layout: imprintBottom
used_in_navigation: true
menu_name: Type utilities
order: 1
---
# Type utilities

The examples on this page describe the usage of the TypeUtils from inside your annotation processor that extends the *de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor* class.

Nevertheless you are able to get a TypeUtils instance via a static getter:

```java
TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);
```

## Retrieve TypeElement and TypeMirror

It can be quite helpful to be able to retrieve TypeElement or TypeMirror by Class or by full qualified class name.
It allows you to compare types and to check if types are assignable to each other.

```java
    // These examples demonstrate the usage from inside your annotation processor that extends
    // the de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor class.
    // Nevertheless the TypeUtils can also be instantiated and used elsewhere by calling the following
    TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

    // get TypeMirrors - will work with all kinds of types
    TypeMirror typeMirror1 = getTypeUtils().doTypeRetrieval().getTypeMirror(String.class);
    TypeMirror typeMirror2 = getTypeUtils().doTypeRetrieval().getTypeMirror("xyz.YourClass");

    // get TypeMirror for primitive type - will return null for none primitive types
    TypeMirror typeMirror3 = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(Long.class);

    // get TypeElements
    TypeElement typeElement1 = getTypeUtils().doTypeRetrieval().getTypeElement(String.class);
    TypeElement typeElement2 = getTypeUtils().doTypeRetrieval().getTypeElement("xyz.YourClass");

    // Will return null because TypeElements for primitive and array types doesn't exist
    TypeElement typeElement3 = getTypeUtils().doTypeRetrieval().getTypeElement(String[].class);
    TypeElement typeElement4 = getTypeUtils().doTypeRetrieval().getTypeElement(Long.class);
```


## Generic types

As stated in the previous section it is not easy to create or a TypeMirror with a specific generic signature.
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

## Comparison of TypeElements and TypeMirrors

TypeMirrors and TypeElements can be checked for equaltity or assignability:

```java
// compare two TypeMirrors - generic TypeParameters will be ignored
boolean result1 = getTypeUtils().doTypeComparison().isErasedTypeEqual(typeMirror1, typeMirror2);

// compare TypeElements and TypeMirrors with either TypeElement, TypeMirror or Class
boolean result2 = getTypeUtils().doTypeComparison().isTypeEqual(typeMirror1, String.class);
boolean result3 = getTypeUtils().doTypeComparison().isTypeEqual(typeMirror1, typeMirror2);
boolean result4 = getTypeUtils().doTypeComparison().isTypeEqual(typeElement1, String.class);
boolean result5 = getTypeUtils().doTypeComparison().isTypeEqual(typeElement1, typeMirror2);
boolean result6 = getTypeUtils().doTypeComparison().isTypeEqual(typeElement1, typeElement2);


// check if types are assignable
boolean result7 = getTypeUtils().doTypeComparison().isAssignableTo(typeElement1, String.class);
boolean result8 = getTypeUtils().doTypeComparison().isAssignableTo(typeElement1, typeMirror2);
boolean result9 = getTypeUtils().doTypeComparison().isAssignableTo(typeElement1, typeElement2);
boolean result10 = getTypeUtils().doTypeComparison().isAssignableTo(typeMirror1, typeMirror2);


// comparison of generic types - passed TypeMirror vs Map<String,String>
getTypeUtils().doTypeComparison().isTypeEqual(
        typeMirror1,
        getTypeUtils().doGenerics().createGenericType(
                Map.class,
                getTypeUtils().doGenerics().createGenericType(String.class),
                getTypeUtils().doGenerics().createGenericType(String.class)
        )

);
getTypeUtils().doTypeComparison().isAssignableTo(
        typeMirror1,
        getTypeUtils().doGenerics().createGenericType(
                Map.class,
                getTypeUtils().doGenerics().createGenericType(String.class),
                getTypeUtils().doGenerics().createGenericType(String.class)
        )

);
```

## Check kind of TypeMirror

The project offers a convenient way to check the kind of a TypeMirror:

```java
boolean result = getTypeUtils().doCheckTypeKind().isPrimitive(typeMirror);
boolean result = getTypeUtils().doCheckTypeKind().isArray(typeMirror);
boolean result = getTypeUtils().doCheckTypeKind().isVoid(typeMirror);
boolean result = getTypeUtils().doCheckTypeKind().isOfTypeKind(typeMirror, typeKind);
```

## Get and check component TypeMirror of an array

The project offers a convenient way to get and check the component type of an array:

```java

// get the arrays component type
TypeMirror typeMirror = getTypeUtils().doArrays().getArraysComponentType(typeMirror1);

// check if an array is of a specific component type (uses exact match)
boolean result = getTypeUtils().doArrays().isArrayOfType(typeMirror1,Class1.class);
boolean result = getTypeUtils().doArrays().isArrayOfType(typeMirror1,"xyz.Class1");
boolean result = getTypeUtils().doArrays().isArrayOfType(typeMirror1,typeMirror2);
boolean result = getTypeUtils().doArrays().isArrayOfType(typeMirror1,
    getTypeUtils().doGenerics().createGenericType(
         Map.class,
         getTypeUtils().doGenerics().createGenericType(String.class),
         getTypeUtils().doGenerics().createGenericType(String.class)
    )
);

// check if an arrays component type is assignable to a specific type
boolean result = getTypeUtils().doArrays().isArrayAssignableTo(typeMirror1,Class1.class);
boolean result = getTypeUtils().doArrays().isArrayAssignableTo(typeMirror1,"xyz.Class1");
boolean result = getTypeUtils().doArrays().isArrayAssignableTo(typeMirror1,typeMirror2);
boolean result = getTypeUtils().doArrays().isArrayAssignableTo(typeMirror1,
    getTypeUtils().doGenerics().createGenericType(
         Map.class,
         getTypeUtils().doGenerics().createGenericType(String.class),
         getTypeUtils().doGenerics().createGenericType(String.class)
    )
);

```