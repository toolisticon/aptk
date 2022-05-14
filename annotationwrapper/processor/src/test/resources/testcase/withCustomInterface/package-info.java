@AnnotationWrapper(value = {TestAnnotation.class}, customInterfaces = {@CustomInterface(annotationToWrap = TestAnnotation.class, interfacesToApply = {Serializable.class, Cloneable.class})})
package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.api.CustomInterface;
import io.toolisticon.cute.TestAnnotation;

import java.io.Serializable;





