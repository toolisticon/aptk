package io.toolisticon.annotationprocessortoolkit.internal;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Wraps all kind framework related tools offeered by {@link ProcessingEnvironment}.
 */

public class FrameworkToolWrapper {

    private final Elements elements;
    private final Filer filer;
    private final Messager messager;
    private final Types types;

    /**
     * Constructor to pass in the {@link ProcessingEnvironment}.
     *
     * @param processingEnv the processing environment to uses
     */
    public FrameworkToolWrapper(ProcessingEnvironment processingEnv) {
        // create local references
        messager = processingEnv.getMessager();
        types = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();

    }

    /**
     * Gets the {@link Elements} utility class instance offered by the {@link ProcessingEnvironment}.
     * @return the {@link Elements} utility class instance
     */
    public Elements getElements() {
        return elements;
    }


    /**
     * Gets the {@link Filer} utility class instance offered by the {@link ProcessingEnvironment}.
     * @return the {@link Filer} utility class instance
     */
    public Filer getFiler() {
        return filer;
    }

    /**
     * Gets the {@link Messager} utility class instance offered by the {@link ProcessingEnvironment}.
     * @return the {@link Messager} utility class instance
     */
    public Messager getMessager() {
        return messager;
    }

    /**
     * Gets the {@link Types} utility class instance offered by the {@link ProcessingEnvironment}.
     * @return the {@link Types} utility class instance
     */
    public Types getTypes() {
        return types;
    }
}
