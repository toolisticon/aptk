package io.toolisticon.annotationprocessortoolkit.generators;

import io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.ProcessingEnvironmentUtils;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Test annotation processor for testing {@link FileObjectUtils}.
 */

@SupportedAnnotationTypes(
        "io.toolisticon.annotationprocessortoolkit.generators.FileObjectUtilsTestAnnotation")
public class FileObjectUtilsTestAnnotationProcessor extends AbstractAnnotationProcessor {

    private SimpleResourceWriter simpleResourceWriter = null;


    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            for (Element element : roundEnv.getElementsAnnotatedWith(FileObjectUtilsTestAnnotation.class)) {

                if (simpleResourceWriter == null) {

                    simpleResourceWriter = FileObjectUtils.createResource("testOutput.txt");

                }

                simpleResourceWriter.append(element.getSimpleName().toString() + "\n");

            }
        } catch (Exception e) {

            MessagerUtils.error(null, "Wasn't able to use SimpleResourceWriter without error");

        } finally {
            try {
                simpleResourceWriter.close();

            } catch (Exception e) {

            }
        }

        return false;
    }


}
