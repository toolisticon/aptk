/**
 * This package contains the fluapigen annotation processor.
 */
@AnnotationWrapper(
        value = {
                On.class,
                OnAnnotationAttributeOfType.class,
                StringMustMatch.class
        })
package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
