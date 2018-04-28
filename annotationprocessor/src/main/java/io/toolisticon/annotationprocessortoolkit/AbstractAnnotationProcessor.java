package io.toolisticon.annotationprocessortoolkit;

import io.toolisticon.annotationprocessortoolkit.tools.ProcessingEnvironmentUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class with support for executing several tasks.
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);


    }

    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {

            ToolingProvider.setTooling(processingEnv);
            return processAnnotations(annotations, roundEnv);

        } finally {
            ToolingProvider.clearTooling();
        }

    }

    public abstract boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    /**
     * Gets the {@link Elements} provided by {@link ProcessingEnvironment}.
     *
     * @return the Elements utility class instance
     */
    public static Elements getElements() {
        return ProcessingEnvironmentUtils.getElements();
    }

    /**
     * Gets the {@link Filer} provided by {@link ProcessingEnvironment}.
     *
     * @return the Filer utility class instance
     */
    public static Filer getFiler() {
        return ProcessingEnvironmentUtils.getFiler();
    }

    /**
     * Gets the {@link Types} provided by {@link ProcessingEnvironment}.
     *
     * @return the Types utility class instance
     */
    public static Types getTypes() {
        return ProcessingEnvironmentUtils.getTypes();
    }

    /**
     * Gets the {@link Messager} provided by {@link ProcessingEnvironment}.
     *
     * @return the Messager utility class instance
     */
    public static Messager getMessager() {
        return ProcessingEnvironmentUtils.getMessager();
    }

    /**
     * Helper function to statically provide supported annotations.
     *
     * @param annotationTypes the annotation types to be added to the set
     * @return the set of supported annotations
     */
    protected static Set<String> createSupportedAnnotationSet(Class<? extends Annotation>... annotationTypes) {
        Set<String> result = new HashSet<String>();

        if (annotationTypes != null) {
            for (Class<? extends Annotation> annotationType : annotationTypes) {

                if (annotationType != null) {
                    result.add(annotationType.getCanonicalName());
                }

            }
        }

        return result;
    }

    /**
     * This method can be used to wrap elements to an array.
     *
     * @param element
     * @param <T>
     * @return
     */
    public static <T> T[] wrapToArray(T... element) {
        return element;
    }

}
