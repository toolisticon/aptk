package de.holisticon.annotationprocessor.internal;

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

    public FrameworkToolWrapper(ProcessingEnvironment processingEnv) {
        // create local references
        messager = processingEnv.getMessager();
        types = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();

    }

    public Elements getElements() {
        return elements;
    }

    public Filer getFiler() {
        return filer;
    }

    public Messager getMessager() {
        return messager;
    }

    public Types getTypes() {
        return types;
    }
}
