/**
 * This package contains the fluapigen annotation processor.
 */
@AnnotationWrapper(
        value = {
                On.class,
                OnAnnotationAttributeOfType.class,
                StringMustMatch.class,
                TargetMustBeAnnotatedWith.class,
                TargetMustBeAnnotatedWiths.class,
                TargetMustBeAssignableTo.class
        })

package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
