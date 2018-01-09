package io.toolisticon.annotationprocessortoolkit.generators;

import io.toolisticon.annotationprocessortoolkit.templating.TemplateProcessor;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by tobiasstamann on 24.08.17.
 */
public class AbstractSimpleWriter<T extends FileObject> {


    private final T fileObject;
    private final Writer foWriter;

    public AbstractSimpleWriter(T fileObject) throws IOException {
        this.fileObject = fileObject;
        this.foWriter = fileObject.openWriter();
    }

    public void append(String content) throws IOException {
        foWriter.append(content);
        foWriter.flush();
    }

    public void write(char[] buffer) throws IOException {
        foWriter.write(buffer);
        foWriter.flush();
    }

    public void writeTemplate( String templateFileName, Map<String, Object> values) throws IOException {
        String processedTemplate = TemplateProcessor.processTemplateResourceFile(templateFileName, values);

        if (processedTemplate != null) {
            append(processedTemplate);
        }

    }

    public void write(String content) throws IOException {
        foWriter.write(content);
        foWriter.flush();
    }

    public void close() throws IOException {
        foWriter.flush();
        foWriter.close();
    }

}
