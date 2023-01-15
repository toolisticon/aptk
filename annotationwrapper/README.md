# Annotation Wrapper

Generates wrapper classes to provide simplified access to annotation attributes.
This is really helpful for accessing type or annotation based attributes.


## Getting started
First please add the processor to your processors compiler configuration:

```xml
<!- maven example -->
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration combine.children="append">
        <verbose>true</verbose>
        <source>${java.compile.source.version}</source>
        <target>${java.compile.target.version}</target>
        <annotationProcessorPaths>
            <path>
                <groupId>io.toolisticon.aptkio.toolisticon.aptk</groupId>
                <artifactId>annotationwrapper-processor</artifactId>
                <version>${aptk.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

It's also required to bind the APTK as dependency:
```xml
<!-- Maven example -->    

<dependency>
    <groupId>io.toolisticon.aptkio.toolisticon.aptk</groupId>
    <artifactId>annotationprocessor</artifactId>
    <version>${aptk.version}</version>
</dependency>   
```

Then you need to add the *AnnotationWrapper* configuration to your processor package *package-info.java* file:
```java
@AnnotationWrapper(value = {YourAnnotation1.class, YourAnnotation2.class})
package your.custompackage;

import AnnotationWrapper;
```
- Generated wrapper classes will have package private visibility by default - this can be changed to public by using the *AnnotationWrapper.usePublicVisibility* attribute.
- By default wrapper classes of all referenced annotation types will be created automatically - this can be deactivated by using the *AnnotationWrapper.automaticallyWrapEmbeddedAnnotations* attribute.

## Extending Wrapper Api
It's possible to extend the wrapper api by custom methods.

Custom methods can be located in any kind of class and must be annotated with the *CustomCodeMethod* annotation, a matching method will be generated in the annotation wrapper that is used to forward calls to the annotated method:

```java
package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public static String forwardedMethod(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

}
```
The annotation takes the targetted annotation type as value attribute.
The annotation must be placed on methods:
- that are non private and static
- must have the targetted annotation wrapper type as first attribute, only the following parameters will later be used in wrapper Api

The custom code classes can be registered in two ways:
- will be picked up automatically if the custom code class is in same package as the targeted wrapper.
- can be defined explicitly in the *AnnotationWrapper* annotation via the *bindCustomCode* attribute. 

```java
@AnnotationWrapper(
        value = {TestAnnotation.class, TestDefaultsAnnotation.class, PrettyExample.class},
        bindCustomCode = {CustomCodeClass.class},
        automaticallyWrapEmbeddedAnnotations = true)
package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
```

## Example

For annotation *PrettyExample*:
```java
package io.toolisticon.aptk.wrapper.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrettyExample {
    String aStringBasedValue();
    Class<?> typeBasedAttribute();
    Class<?>[] typeArrayBasedAttribute() default {};
    EmbeddedAnnotation annotationTypeBasedAttribute();
}
```

and *EmbeddedAnnotation*:
````java
package io.toolisticon.aptk.annotationwrapper.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EmbeddedAnnotation {
    long value();
}
````

the processor will generate the following wrapper class:
```java
package io.toolisticon.aptk.annotationwrapper.test;
import io.toolisticon.aptk.annotationwrapper.test.PrettyExample;
import io.toolisticon.aptk.annotationwrapper.test.EmbeddedAnnotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.TypeUtils;


/**
 * Wrapper class to read attribute values from Annotation PrettyExample.
 */
class PrettyExampleWrapper {

    private final Element annotatedElement;
    private final AnnotationMirror annotationMirror;

    /**
     * Private constructor.
     * Used to read annotation from Element.
     * @param annotatedElement the annotated Element to annotated with this wrapper annotation
     */
    private PrettyExampleWrapper (Element annotatedElement) {
        this.annotatedElement = annotatedElement;
        this.annotationMirror = AnnotationUtils.getAnnotationMirror(annotatedElement, PrettyExample.class);
    }

    /**
     * Private constructor.
     * Mainly used for embedded annotations.
     * @param element the element related with the passed annotationMirror
     * @param annotationMirror the AnnotationMirror to wrap
     */
    private PrettyExampleWrapper (Element element, AnnotationMirror annotationMirror) {
        this.annotatedElement = element;
        this.annotationMirror = annotationMirror;
    }

    /**
     * Gets the element on which the wrapped annotation is used.
     */
    public Element _annotatedElement() {
        return this.annotatedElement;
    }

    /**
     * Gets the wrapped AnnotationMirror.
     */
     public AnnotationMirror _annotationMirror() {
        return this.annotationMirror;
     }

    /**
     * Gets the PrettyExample.aStringBasedValue from wrapped annotation.
     * @return the attribute value
     */
    public String aStringBasedValue() {
        return (String)AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "aStringBasedValue").getValue();
    }



    /**
     * Gets the PrettyExample.typeBasedAttribute from wrapped annotation.
     * @return the attribute value as a TypeMirror
     */
    public TypeMirror typeBasedAttributeAsTypeMirror() {
        return (TypeMirror)AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "typeBasedAttribute").getValue();
    }

    /**
     * Gets the PrettyExample.typeBasedAttribute from wrapped annotation.
     * @return the attribute value as a TypeMirror
     */
    public TypeMirrorWrapper typeBasedAttributeAsTypeMirrorWrapper() {
        return TypeMirrorWrapper.wrap((TypeMirror)AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "typeBasedAttribute").getValue());
    }

    /**
     * Gets the PrettyExample.typeBasedAttribute from wrapped annotation.
     * @return the attribute value as a fqn
     */
    public String typeBasedAttributeAsFqn() {
        return TypeUtils.TypeConversion.convertToFqn(typeBasedAttributeAsTypeMirror());
    }



    /**
     * Gets the PrettyExample.typeArrayBasedAttribute from wrapped annotation.
     * @return the attribute value as a TypeMirror
     */
    public TypeMirror[] typeArrayBasedAttributeAsTypeMirror() {

        List<TypeMirror> result = new ArrayList<>();
        for(AnnotationValue value : (List<AnnotationValue>)AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "typeArrayBasedAttribute").getValue() ) {
            result.add( ((TypeMirror)value.getValue()));
        }

        return result.toArray(new TypeMirror[result.size()]);
    }

    /**
     * Gets the PrettyExample.typeArrayBasedAttribute from wrapped annotation.
     * @return the attribute value as a TypeMirror
     */
    public TypeMirrorWrapper[] typeArrayBasedAttributeAsTypeMirrorWrapper() {

        List<TypeMirrorWrapper> result = new ArrayList<>();
        for(AnnotationValue value : (List<AnnotationValue>)AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "typeArrayBasedAttribute").getValue() ) {
            result.add(TypeMirrorWrapper.wrap((TypeMirror)value.getValue()));
        }

        return result.toArray(new TypeMirrorWrapper[result.size()]);
    }

    /**
     * Gets the PrettyExample.typeArrayBasedAttribute from wrapped annotation.
     * @return the attribute value as a fqn
     */
    public String[] typeArrayBasedAttributeAsFqn() {

        List<String> result = new ArrayList<>();
        for(AnnotationValue value : (List<AnnotationValue>)AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "typeArrayBasedAttribute").getValue() ) {
            result.add( TypeUtils.TypeConversion.convertToFqn((TypeMirror)value.getValue()));
        }

        return result.toArray(new String[result.size()]);
    }


    /**
     * Allows to check if attribute was explicitly set or if default value is used.
     * @return true, if default value is used, otherwise false
     */
    public boolean typeArrayBasedAttributeIsDefaultValue(){
        return AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror,"typeArrayBasedAttribute") == null;
    }

    /**
     * Gets the PrettyExample.annotationTypeBasedAttribute from wrapped annotation.
     * @return the attribute value
     */
    public AnnotationMirror annotationTypeBasedAttributeAsAnnotationMirror() {
        return (AnnotationMirror)(AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "annotationTypeBasedAttribute").getValue());
    }

   /**
     * Gets the PrettyExample.annotationTypeBasedAttribute from wrapped annotation.
     * @return the attribute value
     */
    public EmbeddedAnnotationWrapper annotationTypeBasedAttribute() {
        return EmbeddedAnnotationWrapper.wrap(this.annotatedElement, (AnnotationMirror)(AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "annotationTypeBasedAttribute").getValue()));
    }





    /**
     * Checks if passed element is annotated with this wrapper annotation type : PrettyExample
     * @param element The element to check for wrapped annotation type
     * @return true, if passed element is annotated with PrettyExample annotation, otherwise false
     */
    public static boolean isAnnotated(Element element) {
        return element != null && element.getAnnotation(PrettyExample.class) != null;
    }

     /**
      * Gets the AnnotationMirror from passed element for this wrappers annotation type and creates a wrapper instance.
      * @param element The element to read the annotations from
      * @return The wrapped AnnotationMirror if Element is annotated with this wrappers annotation type, otherwise null.
      */
    public static PrettyExampleWrapper wrap(Element element) {
        return isAnnotated(element) ? new PrettyExampleWrapper(element) : null;
    }

    /**
     * Wraps an AnnotationMirror.
     * Throws an IllegalArgumentException if passed AnnotationMirror type doesn't match the wrapped annotation type.
     * @param annotationMirror The element annotated with the annotation to wrap
     * @return The wrapper instance
     */
    public static PrettyExampleWrapper wrap(AnnotationMirror annotationMirror) {
        return new PrettyExampleWrapper(null, annotationMirror);
    }

   /**
     * Wraps an AnnotationMirror.
     * Throws an IllegalArgumentException if passed AnnotationMirror type doesn't match the wrapped annotation type.
     * @param element the element bound to the usage of passed AnnotationMirror
     * @param annotationMirror The AnnotationMirror to wrap
     * @return The wrapper instance
     */
    public static PrettyExampleWrapper wrap(Element element, AnnotationMirror annotationMirror) {
        return new PrettyExampleWrapper(element, annotationMirror);
    }

}
}
```

So reading of the type attribute is quite easy:
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

// read array based attribute
String[] fqns = wrapper.typeArrayBasedAttributeAsFqn();

// access annotation type based attributes
EmbeddedAnnotationWrapper embeddedAnnotationWrapper = wrapper.annotationTypeBasedAttribute();
long value = embeddedAnnotationWrapper.value();
```

As you can see reading of values and complex annotation hierarchies will be a very easy thing to do.
