@AnnotationWrapper(
        value = {TestAnnotation.class, TestDefaultsAnnotation.class},
        bindCustomCode = {CustomCodeClass.class},
        automaticallyWrapEmbeddedAnnotations = true)
package io.toolisticon.aptk.annotationwrapper.test.annotationonpackage;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.test.TestAnnotation;
import io.toolisticon.aptk.annotationwrapper.test.TestDefaultsAnnotation;




