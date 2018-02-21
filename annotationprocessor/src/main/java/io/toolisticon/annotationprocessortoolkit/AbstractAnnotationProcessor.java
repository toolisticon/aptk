package io.toolisticon.annotationprocessortoolkit;

import io.toolisticon.annotationprocessortoolkit.generators.FileObjectUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class with support for executing several tasks.
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {


    private Elements elementUtils;
    private Filer filer;
    private MessagerUtils messager;
    private FileObjectUtils fileObjectUtils;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        // create local references
        messager = new MessagerUtils();
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        fileObjectUtils = FileObjectUtils.getFileObjectUtils();

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
     * Gets the {@link MessagerUtils}.
     *
     * @return the MessagerUtils
     */
    public MessagerUtils getMessager() {
        return messager;
    }



    /**
     * Gets the {@link Elements} provided by {@link ProcessingEnvironment}.
     *
     * @return the Elements utils
     */
    public Elements getElements() {
        return elementUtils;
    }

    /**
     * Gets the {@link Filer} provided by {@link ProcessingEnvironment}.
     *
     * @return theFiler urils
     */
    public Filer getFiler() {
        return filer;
    }

    /**
     * Gets the {@link FileObjectUtils}.
     *
     * @return the FileObjectUtils.
     */
    public FileObjectUtils getFileObjectUtils() {
        return fileObjectUtils;
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
