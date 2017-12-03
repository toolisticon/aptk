package de.holisticon.annotationprocessortoolkit.generators;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.StandardLocation;
import java.io.IOException;

/**
 * Utility class for handling {@link javax.tools.FileObject}.
 */
public class FileObjectUtils {

    private final FrameworkToolWrapper frameworkToolWrapper;

    /**
     * Hidden constructor that prevents instantiation.
     *
     * @param frameworkToolWrapper the framework tool wrapper
     */
    private FileObjectUtils(FrameworkToolWrapper frameworkToolWrapper) {
        this.frameworkToolWrapper = frameworkToolWrapper;
    }

    /**
     * Gets a SimpleResourceReader for reading a resource file.
     *
     * @param fileName      the file name of the resource to be opened
     * @param targetPackage the target package
     * @return SimpleResourceReader if resource exists and can be read (== not opened for writing)
     */
    public SimpleResourceReader getResource(String fileName, String targetPackage) throws IOException {
        return getResource(fileName, targetPackage, StandardLocation.SOURCE_OUTPUT);
    }

    /**
     * Gets a SimpleResourceReader for reading a resource file.
     *
     * @param fileName         the file name of the resource to be opened
     * @param targetPackage    the target package
     * @param standardLocation the location to read the resource from
     * @return SimpleResourceReader if resource exists and can be read (== not opened for writing)
     */
    public SimpleResourceReader getResource(String fileName, String targetPackage, StandardLocation standardLocation) throws IOException {
        return new SimpleResourceReader(frameworkToolWrapper.getFiler().getResource(standardLocation, targetPackage != null ? targetPackage : "", fileName));
    }

    /**
     * Gets a SimpleResourceReader for reading a resource file.
     *
     * @param fileName the file name of the resource to be opened
     * @return SimpleResourceReader if resource exists and can be read (== not opened for writing)
     */
    public SimpleResourceReader getResource(String fileName) throws IOException {
        return getResource(fileName, "");
    }

    /**
     * Creates a resource file resident in CLASS_OUTPUT in root package with give name.
     *
     * @param fileName            the file name to use
     * @param targetPackage       the target package to use
     * @param originatingElements the originating elements responsible for resource file creation.
     * @return SimpleResourceWriter if it hasn't been created before.
     * @throws IOException
     * @throws javax.annotation.processing.FilerException if the same pathname has already been created
     */
    public SimpleResourceWriter createResource(String fileName, String targetPackage, Element... originatingElements) throws IOException {
        return createResource(fileName, targetPackage, StandardLocation.CLASS_OUTPUT);
    }

    /**
     * Creates a resource file resident in CLASS_OUTPUT in root package with give name.
     *
     * @param fileName            the file name to use
     * @param targetPackage       the target package to use
     * @param location            the location to write the file to.
     * @param originatingElements the originating elements responsible for resource file creation.
     * @return SimpleResourceWriter if it hasn't been created before.
     * @throws IOException
     * @throws javax.annotation.processing.FilerException if the same pathname has already been created
     */
    public SimpleResourceWriter createResource(String fileName, String targetPackage, StandardLocation location, Element... originatingElements) throws IOException {
        return new SimpleResourceWriter(frameworkToolWrapper.getFiler().createResource(location, targetPackage != null ? targetPackage : "", fileName, originatingElements));
    }

    /**
     * Creates a resource file resident in SOURCE_OUTPUT in root package with give name.
     *
     * @param fileName the file name to use
     * @return SimpleResourceWriter if it hasn't been created before.
     * @throws IOException
     * @throws javax.annotation.processing.FilerException if the same pathname has already been created
     */
    public SimpleResourceWriter createResource(String fileName) throws IOException {
        return createResource(fileName, "");
    }


    public SimpleJavaWriter createClassFile(String fileName, Element... originatingElements) throws IOException {
        return new SimpleJavaWriter(frameworkToolWrapper.getFiler().createClassFile(fileName, originatingElements));
    }

    public SimpleJavaWriter createSourceFile(String fileName, Element... originatingElements) throws IOException {
        return new SimpleJavaWriter(frameworkToolWrapper.getFiler().createSourceFile(fileName, originatingElements));
    }

    /**
     * Gets an instance of this TypeUtils class.
     *
     * @param frameworkToolWrapper the wrapper instance that provides the {@link javax.annotation.processing.ProcessingEnvironment} tools
     * @return the type utils instance
     */
    public static FileObjectUtils getFileObjectUtils(FrameworkToolWrapper frameworkToolWrapper) {
        return new FileObjectUtils(frameworkToolWrapper);
    }

    /**
     * Gets an instance of a TypeUtils class.
     *
     * @param processingEnvironment the processing environment to use
     * @return the type utils instance
     */
    public static FileObjectUtils getTypeUtils(ProcessingEnvironment processingEnvironment) {
        return new FileObjectUtils(new FrameworkToolWrapper(processingEnvironment));
    }

}
