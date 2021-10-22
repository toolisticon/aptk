@AnnotationWrapper(
        value = {TestAnnotation.class, TestDefaultsAnnotation.class},
        bindCustomCode = {
                @BindCustomCode(
                        annotation = TestAnnotation.class,
                        customCodeClass = {CustomCodeClass.class}
                )
        },
        automaticallyWrapEmbeddedAnnotations = true)
package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.api.BindCustomCode;



