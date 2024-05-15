/**
 * This package contains the fluapigen annotation processor.
 */
@AnnotationWrapper(
        value = {
                WithTargetOfKind.class,
                WithAnnotationAttributeTargetOfType.class,
                WithStringMatching.class,
                WithTargetElementAnnotatedWith.class,
                WithTargetElementAnnotatedWithRepeatable.class,
                WithTargetElementAssignableTo.class,
                WithIntegerInBounds.class,
                WithLongInBounds.class,
                WithFloatInBounds.class,
                WithDoubleInBounds.class
        })

package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
