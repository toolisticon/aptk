@AnnotationWrapper(value = {TestAnnotation.class}, bindCustomCode = {@BindCustomCode(annotation = TestAnnotation.class, customCodeClass = {CustomCodeClass.class})}, automaticallyWrapEmbeddedAnnotations = false)
package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.api.BindCustomCode;
import io.toolisticon.aptk.annotationwrapper.processor.AnnotationWrapperProcessor;
import io.toolisticon.cute.TestAnnotation;



