package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.MessagerUtils;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;
import de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
import de.holisticon.annotationprocessortoolkit.validators.FluentModifierElementValidator;
import de.holisticon.annotationprocessortoolkit.validators.FluentTypeElementValidator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract base class with support for executing several tasks.
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {


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
        return new FluentExecutableElementValidator(frameworkToolWrapper, methodElement);
    }


    /**
     * Creates fluent validator for validating modifiers of elements.
     *
     * @param element the element instance to validate
     * @return FluentTypeElementValidator instance
     */
    public FluentModifierElementValidator getFluentModifierElementValidator(Element element) {
        return new FluentModifierElementValidator(frameworkToolWrapper, element);
    }

    /**
     * Creates fluent validator for TypeElements.
     *
     * @param typeElement the TypeElement instance to validate
     * @return FluentMethodValidator instance
     */
    public FluentTypeElementValidator getFluentTypeValidator(TypeElement typeElement) {
        return new FluentTypeElementValidator(frameworkToolWrapper, typeElement);
    }


    private Elements elementUtils;
    private Filer filer;
    private FrameworkToolWrapper frameworkToolWrapper;
    private MessagerUtils messager;
    private TypeUtils typeUtils;


    public static class FileObjectWrapper {
        private final FileObject fileObject;
        private final Writer foWriter;

        public FileObjectWrapper(FileObject fileObject) throws IOException {
            this.fileObject = fileObject;
            this.foWriter = fileObject.openWriter();
        }

        public void append(String content) throws IOException {
            foWriter.append(content);
            foWriter.flush();
        }

        public void close() throws IOException {
            foWriter.flush();
            foWriter.close();
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        frameworkToolWrapper = new FrameworkToolWrapper(processingEnv);
        // create local references
        messager = new MessagerUtils(processingEnv.getMessager());
        typeUtils = TypeUtils.getTypeUtils(frameworkToolWrapper);
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    public MessagerUtils getMessager() {
        return messager;
    }

    public TypeUtils getTypeUtils() {
        return typeUtils;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public Filer getFiler() {
        return filer;
    }

    public FrameworkToolWrapper getFrameworkToolWrapper() {
        return frameworkToolWrapper;
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
