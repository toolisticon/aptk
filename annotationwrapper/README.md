# Annotation Wrapper

Generates wrapper classes for making reading of annotations more user friendly.

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
                <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
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
    <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
    <artifactId>annotationprocessor</artifactId>
    <version>${aptk.version}</version>
</dependency>   
```

Then you need to add the *AnnotationWrapper* configuration to your processor package *package-info.java* file:
```java
@AnnotationWrapper(value = {YourAnnotation1.class, YourAnnotation2.class})
package your.custompackage;

import io.toolisticon.annotationprocessortoolkit.wrapper.api.AnnotationWrapper;
```
- Generated wrapper classes will have package private visibility by default - this can be changed to public by using the *AnnotationWrapper.usePublicVisibility* attribute.
- By default wrapper classes of all referenced annotation types will be created automatically - this can be deactivated by using the *AnnotationWrapper.automaticallyWrapEmbeddedAnnotations* attribute.

##Example

For annotation *TestAnnotation*:
```java
package io.toolisticon.annotationprocessortoolkit.wrapper.test;

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

the processor will generate the following wrapper class:
```java
package io.toolisticon.annotationprocessortoolkit.wrapper.test;
import io.toolisticon.annotationprocessortoolkit.wrapper.test.PrettyExample;
import io.toolisticon.annotationprocessortoolkit.wrapper.test.EmbeddedAnnotation;

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
import io.toolisticon.annotationprocessortoolkit.tools.AnnotationUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeMirrorWrapper;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;


/**
 * Wrapper class to read attribute values from Annotation {@see PrettyExample}.
 */
class PrettyExampleWrapper {

    private final Element annotatedElement;
    private final AnnotationMirror annotationMirror;

    /**
     * Private constructor.
     * Used to read annotation from Element.
     */
    private PrettyExampleWrapper (Element annotatedElement) {
        this.annotatedElement = annotatedElement;
        this.annotationMirror = AnnotationUtils.getAnnotationMirror(annotatedElement, PrettyExample.class);
    }

    /**
     * Private constructor.
     * Mainly used for embedded annotations.
     */
    private PrettyExampleWrapper (AnnotationMirror annotationMirror) {
        this.annotatedElement = null;
        this.annotationMirror = annotationMirror;
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
        return EmbeddedAnnotationWrapper.wrapAnnotationMirror((AnnotationMirror)(AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "annotationTypeBasedAttribute").getValue()));
    }





    /**
     * Checks if passed element is annotated with this wrapper annotation type : PrettyExample
     * @return true, if passed element is annotated with PrettyExample annotation, otherwise false
     */
    public static boolean isAnnotated(Element element) {
        return element != null && element.getAnnotation(PrettyExample.class) != null;
    }

     /**
      * Gets the AnnotationMirror from passed element for this wrappers annotation type and creates a wrapper instance.
      *
      * @return The wrapped AnnotationMirror if Element is annotated with this wrappers annotation type, otherwise null.
      */
    public static PrettyExampleWrapper wrapAnnotationOfElement(Element element) {
        return isAnnotated(element) ? new PrettyExampleWrapper(element) : null;
    }

    /**
     * Wraps an AnnotationMirror.
     * Throws an IllegalArgumentException if passed AnnotationMirror type doesn't match the wrapped annotation type.
     *
     * @return The wrapper instance
     */
    public static PrettyExampleWrapper wrapAnnotationMirror(AnnotationMirror annotationMirror) {
        return new PrettyExampleWrapper(annotationMirror);
    }

}
```

So reading of the type attribute is quite easy:
```java
PrettyExampleWrapper.wrap(element).typeBasedAttributeAsTypeMirror();
```

As you can see reading of complex annotation hierarchies will be very easy.
