package io.toolisticon.aptk.tools.generators;

import io.toolisticon.aptk.common.ToolingProvider;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple writer for writing kotlin source files.
 */
public class SimpleKotlinWriter extends SimpleWriter {

    final static String KAPT_KOTLIN_GENERATED = "kapt.kotlin.generated";
    private final static Pattern PACKAGE_PATTTERN = Pattern.compile("(?:(\\w+?(?:[.]\\w+?)*)[.])\\w*");
    private final static Pattern CLASSNAME_PATTTERN = Pattern.compile("(?:(?:\\w+?[.])*)(\\w*)");

    private final String fqn;
    private final Element[] originatingElements;
    private final String kaptKotlinGeneratedDirectory;

    private File targetFile;

    public SimpleKotlinWriter(String fqn, Element... originatingElements) {

        this.fqn = fqn;
        this.originatingElements = originatingElements;

        ToolingProvider toolingProvider = ToolingProvider.getTooling();
        kaptKotlinGeneratedDirectory = toolingProvider.getProcessingEnvironment().getOptions().get(KAPT_KOTLIN_GENERATED);

        if (kaptKotlinGeneratedDirectory == null) {
            toolingProvider.getMessager().printMessage(Diagnostic.Kind.ERROR, "Cannot determine kotlin generated folder: Please make sure that KAPT plugin is configured properly ", this.originatingElements!= null && this.originatingElements.length >=1?this.originatingElements[0]:null);
        } else {
            File targetPackageDir = createTargetPackageDirectory();
            targetFile = new File(targetPackageDir, getClassNameFromFqn(fqn) + ".kt");
        }

    }

    @Override
    public void append(String content) throws IOException {
        if(kaptKotlinGeneratedDirectory == null) {
            return;
        }
        Files.write(targetFile.toPath(), content.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    @Override
    public void write(char[] buffer) throws IOException {
        if(kaptKotlinGeneratedDirectory == null) {
            return;
        }
        byte[] byteBuffer = new String(buffer).getBytes(StandardCharsets.UTF_8);
        Files.write(targetFile.toPath(), byteBuffer, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public void write(String content) throws IOException {
        if(kaptKotlinGeneratedDirectory == null) {
            return;
        }
        Files.write(targetFile.toPath(), content.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private void createFile() throws IOException {
        Files.createFile(targetFile.toPath());
    }

    @Override
    public void close() throws IOException {
        // Nothing to do
    }

    private File createTargetPackageDirectory() {
        File targetPackageDirectory = getTargetPackageDirectoryFile();
        if (!targetPackageDirectory.exists()) {
            targetPackageDirectory.mkdirs();
        }

        return targetPackageDirectory;

    }

    static String getPackageFromFqn(String fqn) {
        Matcher matcher = PACKAGE_PATTTERN.matcher(fqn);
        return matcher.matches() ? matcher.group(1) : "";
    }

    static String getClassNameFromFqn(String fqn) {
        Matcher matcher = CLASSNAME_PATTTERN.matcher(fqn);
        return matcher.matches() ? matcher.group(1) : fqn;
    }

    File getTargetPackageDirectoryFile() {
        return new File(this.kaptKotlinGeneratedDirectory + File.separator + getPackageFromFqn(this.fqn).replaceAll("[.]", File.separator));
    }

    public File getTargetFile() {
        return targetFile;
    }
}
