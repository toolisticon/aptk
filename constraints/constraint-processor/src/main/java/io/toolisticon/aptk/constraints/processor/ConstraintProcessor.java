package io.toolisticon.aptk.constraints.processor;

import io.toolisticon.aptk.constraints.BasicConstraints;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.spiap.api.SpiService;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.Set;

/**
 * Annotation processor to generate wrapper class that eases reading of annotations attributes.
 * This circumvents the need to read attribute values by AnnotationMirror/Value api.
 */
@SpiService(Processor.class)
public class ConstraintProcessor extends AbstractAnnotationProcessor {

    /**
     * The supported annotation types.
     */
    private final static Set<String> SUPPORTED_ANNOTATIONS = Collections.singleton("*");

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }


    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (!roundEnv.processingOver()) {
            for (TypeElement typeElement : annotations) {

                for (Element annotatedElements : roundEnv.getElementsAnnotatedWith(typeElement)) {

                    BasicConstraints.checkConstraints(annotatedElements);

                }

            }
        }

        return false;
    }
}