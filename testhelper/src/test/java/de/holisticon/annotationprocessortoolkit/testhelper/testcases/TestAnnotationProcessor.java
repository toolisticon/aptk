package de.holisticon.annotationprocessortoolkit.testhelper.testcases;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Test annotation processor. Does nothing.
 */

@SupportedAnnotationTypes("de.holisticon.annotationprocessortoolkit.testhelper.testcases.TestAnnotation")
public class TestAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
