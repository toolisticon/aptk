@AnnotationWrapper(
        value = {TestAnnotation.class, TestDefaultsAnnotation.class},
        bindCustomCode = {CustomCodeClass.class},
        automaticallyWrapEmbeddedAnnotations = true)
package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;



