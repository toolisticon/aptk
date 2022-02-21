package io.toolisticon.aptk.annotationwrapper.test.annotationontype;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.test.TestAnnotation;
import io.toolisticon.aptk.annotationwrapper.test.TestDefaultsAnnotation;


@AnnotationWrapper(
        value = {TestAnnotation.class, TestDefaultsAnnotation.class},
        bindCustomCode = {CustomCodeClass.class},
        automaticallyWrapEmbeddedAnnotations = true)

public class PackagePlaceholder {
}
