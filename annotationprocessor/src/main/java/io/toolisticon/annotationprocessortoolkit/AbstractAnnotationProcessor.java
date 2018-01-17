package io.toolisticon.annotationprocessortoolkit;

import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.generators.FileObjectUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
import io.toolisticon.annotationprocessortoolkit.validators.FluentModifierElementValidator;
import io.toolisticon.annotationprocessortoolkit.validators.FluentTypeElementValidator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract base class with support for executing several tasks.
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {


    private Elements elementUtils;
    private Filer filer;
    private MessagerUtils messager;
    private TypeUtils typeUtils;
    private FileObjectUtils fileObjectUtils;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        // create local references
        messager = new MessagerUtils();
        typeUtils = TypeUtils.getTypeUtils();
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
     * Gets the {@link TypeUtils}.
     *
     * @return the TypeUtils
     */
    public TypeUtils getTypeUtils() {
        return typeUtils;
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
     * Creates fluent filter for Element Lists.
     *
     * @param elementListToFilter the element list to filter
     * @param <E>
     * @return The filtered list
     */
    public <E extends Element> FluentElementFilter<E> createFluentElementFilter(List<E> elementListToFilter) {
        return FluentElementFilter.createFluentFilter(elementListToFilter);
    }


    /**
     * Creates fluent validator for method Element.
     *
     * @param methodElement the ExecutableElement instance to validate
     * @return FluentMethodValidator instance
     */
    public FluentExecutableElementValidator getFluentMethodValidator(ExecutableElement methodElement) {
        return new FluentExecutableElementValidator(methodElement);
    }


    /**
     * Creates fluent validator for validating modifiers of elements.
     *
     * @param element the element instance to validate
     * @return FluentTypeElementValidator instance
     */
    public FluentModifierElementValidator getFluentModifierElementValidator(Element element) {
        return new FluentModifierElementValidator(element);
    }

    /**
     * Creates fluent validator for TypeElements.
     *
     * @param typeElement the TypeElement instance to validate
     * @return FluentMethodValidator instance
     */
    public FluentTypeElementValidator getFluentTypeValidator(TypeElement typeElement) {
        return new FluentTypeElementValidator(typeElement);
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


}
