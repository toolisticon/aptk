package io.toolisticon.annotationprocessortoolkit.generators;

import io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;

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
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            for (Element element : roundEnv.getElementsAnnotatedWith(FileObjectUtilsTestAnnotation.class)) {

                if (simpleResourceWriter == null) {

                    simpleResourceWriter = FileObjectUtils.getTypeUtils(processingEnv).createResource("testOutput.txt");


                }


                simpleResourceWriter.append(element.getSimpleName().toString() + "\n");


            }
        } catch (Exception e) {
            getMessager().error(null, "Wasn't able to use SimpleResourceWriter without error");
        } finally {
            try {
                simpleResourceWriter.close();

            } catch (Exception e) {

            }
        }

        return false;
    }


}
