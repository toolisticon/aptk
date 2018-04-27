package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.ToolingProvider;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Convenience class to access tools offered by ProcessingEnvionemt via {@link io.toolisticon.annotationprocessortoolkit.ToolingProvider}.
 */
public final class ProcessingEnvironmentUtils {

    /**
     * Hidden constructor.
     */
    private ProcessingEnvironmentUtils() {

    }

    /**
     * Gets Elements tools.
     *
     * @return the Elements instance
     */
    public static Elements getElements() {
        return ToolingProvider.getTooling().getElements();
    }

    /**
     * Gets Elements tools.
     *
     * @return the Elements instance
     */
    public static Filer getFiler() {
        return ToolingProvider.getTooling().getFiler();
    }

    /**
     * Gets Types tools.
     *
     * @return the Types instance
     */
    public static Types getTypes() {
        return ToolingProvider.getTooling().getTypes();
    }

    /**
     * Gets Messager tools.
     *
     * @return the Messager instance
     */
    public static Messager getMessager() {
        return ToolingProvider.getTooling().getMessager();
    }


}
