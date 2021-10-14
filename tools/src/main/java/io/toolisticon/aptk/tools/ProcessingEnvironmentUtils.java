package io.toolisticon.aptk.tools;

import com.sun.source.util.Trees;
import io.toolisticon.aptk.common.ToolingProvider;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Convenience class to access tools offered by ProcessingEnvionemt via {@link ToolingProvider}.
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


    /**
     * Gets processing environment.
     *
     * @return The trees instance
     */
    public static ProcessingEnvironment getProcessingEnvironment() {
        return ToolingProvider.getTooling().getProcessingEnvironment();
    }

    /**
     * Gets Trees instance.
     *
     * @return The trees instance
     */
    public static Trees getTrees() {
        return Trees.instance(ToolingProvider.getTooling().getProcessingEnvironment());
    }


}
