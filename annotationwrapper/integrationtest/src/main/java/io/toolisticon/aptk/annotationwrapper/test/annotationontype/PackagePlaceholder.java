package io.toolisticon.aptk.annotationwrapper.test.annotationontype;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.test.ExampleTestAnnotation;
import io.toolisticon.aptk.annotationwrapper.test.TestDefaultsAnnotation;


@AnnotationWrapper(
        value = {ExampleTestAnnotation.class, TestDefaultsAnnotation.class},
        bindCustomCode = {CustomCodeClass.class},
        automaticallyWrapEmbeddedAnnotations = true)

public class PackagePlaceholder {
}
