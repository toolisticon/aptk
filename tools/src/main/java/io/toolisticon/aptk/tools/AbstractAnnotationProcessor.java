package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.HashSet;
import java.util.Optional;
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
    @SafeVarargs
    protected static Set<String> createSupportedAnnotationSet(Class<? extends Annotation>... annotationTypes) {
        Set<String> result = new HashSet<>();

        if (annotationTypes != null) {
            for (Class<? extends Annotation> annotationType : annotationTypes) {

                // skip null values
                if(annotationType == null) {
                    continue;
                }

                result.add(annotationType.getCanonicalName());

                Repeatable repeatableAnnotation = annotationType.getAnnotation(Repeatable.class);
                if (repeatableAnnotation != null) {
                    result.add(repeatableAnnotation.value().getCanonicalName());
                }

            }
        }

        return result;
    }

    /**
     * Gets all elements from round environment that are annotated with passed annotation or its wrapper type in case of a repeatable annotation.
     *
     * @param roundEnvironment the round environment to get the elements under processing from
     * @param annotation       the annotation type
     * @return a set containing all annotated elements annotated with either the passed annotation or its repeatable wrapper type.
     */
    public static Set<Element> getAnnotatedElements(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation) {

        Set<Element> result = new HashSet<>(roundEnvironment.getElementsAnnotatedWith(annotation));

        Optional<Class<? extends Annotation>> wrapperAnnotationType = AnnotationUtils.getRepeatableAnnotationWrapperClass(annotation);
        if (AnnotationUtils.isRepeatableAnnotation(annotation)) {
            result.addAll(roundEnvironment.getElementsAnnotatedWith(wrapperAnnotationType.get()));
        }

        return result;
    }


    /**
     * This method can be used to wrap elements to an array.
     *
     * @param element the elements to be wrapped
     * @param <T>     the type of the elements
     * @return the array containing al passed elements
     * @deprecated Use getAnnotationClasses for providing supported annotations instead.
     */
    @SafeVarargs
    public static <T> T[] wrapToArray(T... element) {
        return element;
    }

}
