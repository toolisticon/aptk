package io.toolisticon.aptk.tools.generators;

import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.FilerUtils;
import io.toolisticon.aptk.tools.MessagerUtils;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Test annotation processor for testing {@link FilerUtils}.
 */

@SupportedAnnotationTypes(
        "io.toolisticon.aptk.tools.generators.FileObjectUtilsTestAnnotation")
public class FileObjectUtilsTestAnnotationProcessor extends AbstractAnnotationProcessor {

    private SimpleResourceWriter simpleResourceWriter = null;


    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            for (Element element : roundEnv.getElementsAnnotatedWith(FileObjectUtilsTestAnnotation.class)) {

                if (simpleResourceWriter == null) {

                    simpleResourceWriter = FilerUtils.createResource("testOutput.txt");

                }

                simpleResourceWriter.append(element.getSimpleName().toString() + System.lineSeparator());

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
