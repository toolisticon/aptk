# Annotation-Processor-Toolkit Example Annotations

This is an example how to build an annotation processor using the annotation-processor-toolkit.
But in general it might be a good starting point to understand how annotation processor are working and how they are created.

## About annotation processors
Java offers the possibility to process annotated source code via annotation processor during compile time.
The annotation processors can be used to

- check if annotations are used correctly and to throw compile errors and warnings in case of broken constraints
- generate resources and source code based on annotations

Generation of code and resources has some limitations. You just can create new classes and resources. It's not possible to change the contract of an existing class.

Another thing that you have to keep in mind is that you have to cope with java runtime and compile time model since not all classes are compiled already.

Annotation processing itself is done in multiple rounds. Annotation processors are able to claim an annotation and to skip processing by other processors.

## How to implement an annotation processor
Lookup of annotation processors during the compile process is done via a service provider interface (SPI) *javax.annotation.processing.Processor*

Therefore you must create a resource *META-INF.services/javax.annotaion.processing.Processor* that contains the full qualified class names of your annotation processor classes.

Doing this manually has a drawback - you can't use annotation processor in your project anymore (because your annnotation processor is registered before it is compiled) and therefore you must set the proc:none flag in the compile plugin. 

You can circumvent this by using a tool like [SPIAP](https://github.com/toolisticon/SPI-Annotation-Processor) or [google's auto service](https://github.com/google/auto/tree/master/service) which does create the services file via an annotation processor for you.

Your annotation processor classes must extend the *javax.annotation.processing.AbstractProcessor* class to enable java to use it.
Alternatively - if you wan't to use this toolkit you must extend the *de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor* class.

### How to tell Java which annotations will be processed by the annotation processor
There are multiple ways to define which annotations have to be implement.

#### By annotation
You can add the *SupportedAnnotationTypes* annotation to your annotation processor class. It will be evaluated inside the *AbstractProcessor.getSupportedAnnotationTypes()* method.
One drawback of doing this is that supported annotations are defined as a String and it's not very refactoring safe.

#### By overwriting AbstractProcessor.getSupportedAnnotationTypes()
You can define supported annotaions just by overwriting the *AbstractProcessor.getSupportedAnnotationTypes()* method.

### How to process annotations
Java calls the following method of your annotation processor if an element annotated with a supported annotation is detected.

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)

You need to overwrite this method to apply your annotation processorlogic.

The *annotations* parameter contains all annotations that are should currently be processed by the annotation processor.

The *roundEnv* parameter provides the environment of the compilation.  It allows access to all annotated Elements that need to be processed and to check state of previous or current processing rounds.

The boolean return type defines if the annotation is claimed exclusively by this annotation processor, i.e. it disabled the processing of the annotation by other annotation processors.

Since not all classes are already compile during annotation processing you need to cope with the Java compile time model.
Java passes a *ProcessingEnvironment* instance to the *AbstractProcessor.init* method that provides access to some very useful tools.
The *ProcessingEnvironment* can be accessed by the protected *AbstractProcessor.processingEnv* field.
It offers access for tools for
- creation of compile time messages
- handling compile time model
- creation of source code and resources











