package io.toolisticon.annotationprocessortoolkit;


import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Provides {@link ProcessEnvironment} tools.
 */
public class ToolingProvider {

    private static ThreadLocal<ToolingProvider> tooling = new ThreadLocal<ToolingProvider>();

    public static ToolingProvider getTooling() {
        return tooling.get();
    }

    public static void setTooling(ProcessingEnvironment processingEnvironment) {
        tooling.set(new ToolingProvider(processingEnvironment));
    }

    public static void clearTooling() {
        tooling.remove();
    }


    private final Elements elements;
    private final Filer filer;
    private final Messager messager;
    private final Types types;

    /**
     * Constructor to pass in the {@link ProcessingEnvironment}.
     *
     * @param processingEnv the processing environment to uses
     */
    public ToolingProvider(ProcessingEnvironment processingEnv) {
        // create local references
        messager = processingEnv.getMessager();
        types = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();

    }

    /**
     * Gets the {@link Elements} utility class instance offered by the {@link ProcessingEnvironment}.
     *
     * @return the {@link Elements} utility class instance
     */
    public Elements getElements() {
        return elements;
    }


    /**
     * Gets the {@link Filer} utility class instance offered by the {@link ProcessingEnvironment}.
     *
     * @return the {@link Filer} utility class instance
     */
    public Filer getFiler() {
        return filer;
    }

    /**
     * Gets the {@link Messager} utility class instance offered by the {@link ProcessingEnvironment}.
     *
     * @return the {@link Messager} utility class instance
     */
    public Messager getMessager() {
        return messager;
    }

    /**
     * Gets the {@link Types} utility class instance offered by the {@link ProcessingEnvironment}.
     *
     * @return the {@link Types} utility class instance
     */
    public Types getTypes() {
        return types;
    }

}
