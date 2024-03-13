package io.toolisticon.aptk.constraints;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * This spi is used to provide manual check for constraints. This can for example be checks for correct usage of the constraint annotations itself.
 * Additionally, it can be checks that aren*t available via constraint annotations
 * But
 */
public interface ManualConstraintSpi {



    /**
     * Get the annotation supported by this spi implementation.
     *
     * @return the supported annotation
     */
    Class<? extends Annotation> getSupportedAnnotation();


    /**
     * Do some manual checks for constraints. This can be checks if annotation constraint is used correctly
     * @param annotatedElement the annotated element
     * @param constraintAnnotationMirror the AnnotationMirror representing the constraint annotation
     */
    void checkManualConstraints(Element annotatedElement, AnnotationMirror constraintAnnotationMirror);




}
