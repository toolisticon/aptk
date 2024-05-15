# WARNING - THIS FEATURE IS STILL IN EXPERIMENTAL AND MIGHT BE REMOVED

# Constraints on Annotations
A lot of the commonly used tools in Java development are somehow based on annotations. 
In most cases there are kind of constraints about how those annotations must be used.

One example is the Target annotation provided by Java itself which is used to allow annotations to be placed on certain kind of Elements.
But there are other kinds of possible constraints like that annotated types must implement a specific interface or that a String based annotation attribute must not be empty.
Such kind of constraints often just mentioned in the Javadoc and errors might only be triggered at runtime - this is the case for most tools which doesn't have an annotation processor involved.

If there is an annotation processor present,  it will be possible to check those constraints during the processing and trigger compiler messages in the compilation process.
This can be warnings or even errors that make the compilation fail.

This subproject of APTK tries to ease applying and documenting of annotation constraints by providing general purpose constraint annotations which can be placed on annotation types or attributes.

Just think about the Java's _Target_ annotation again. It allows restraining the usage on types by declaring:

```java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestAnnotation {

}
```

Unfortunately the _ElementType.TYPE_ is kind of ambiguous and represents classes, interfaces, enums and records. 
This tool provides the _On_ constraint annotation which can be used to further restrict the usage of annotations.
So if annotations must only be applicable on interfaces rather than on classes, enums and records.

```java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.toolisticon.aptk.constraints.WithTargetOfKind;
import io.toolisticon.aptk.constraints.WithTargetOfKind.TargetKind;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@On(Location.INTERFACE)
public @interface TestAnnotation {
   
}
```

The library provides a standalone annotation processor that scans all annotations if they are annotated with any kind of constraint annotation. Additionally, it's possible to embed the validation into an existing processor.

A constraint annotation is just an annotation that is annotated with the _Constraint_ meta annotation. It must be either be placeable on annotations or annotation attributes.
It's also possible to use constraint annotations on other constraint annotations.

The constraint implementation will be bound to the constraint annotation via the _AnnotationConstraintSpi_ SPI. By doing this it's relatively easy to provide custom constraints.

In some cases it's useful to manually implement constraints for some annotations. This can be done by implementing the _ManualConstraintSpi_ SPI.

If we take a look at the _On_ annotation we will see that it is implenting both SPIs. One for applying constraints on other annotations and the other to make sure that it itself is used correctly.

