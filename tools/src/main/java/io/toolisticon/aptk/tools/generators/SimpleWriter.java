package io.toolisticon.aptk.tools.generators;

import io.toolisticon.aptk.templating.TemplateProcessor;

import java.io.IOException;
import java.util.Map;

/**
 * Abstract base class for writers of java code and resources.
 */
public abstract class SimpleWriter {


    /**
     * Write a template based content.
     *
     * @param templateFileName the template resource file to use
     * @param values           the values to be used with template
     * @throws IOException is thrown if content can't be written
     */
    public void writeTemplate(String templateFileName, Map<String, Object> values) throws IOException {
        String processedTemplate = TemplateProcessor.processTemplateResourceFile(templateFileName, values);

        if (processedTemplate != null) {
            append(processedTemplate);
        }

    }

    /**
     * Write a template based content.
     *
     * @param templateString the template string to use
     * @param values         the values to be used with template
     * @throws IOException is thrown if content can't be written
     */
    public void writeTemplateString(String templateString, Map<String, Object> values) throws IOException {
        String processedTemplate = TemplateProcessor.processTemplate(templateString, values);

        if (processedTemplate != null) {
            append(processedTemplate);
        }

    }
    
    
    /**
     * Appends string content to the writer.
     *
     * @param content the content to append
     * @throws IOException is thrown if content can't be written
     */
    public abstract void append(String content) throws IOException;

    /**
     * Write char array content to the writer.
     *
     * @param buffer the buffer to append
     * @throws IOException is thrown if buffer can't be written
     */
    public abstract void write(char[] buffer) throws IOException;
    
    /**
     * Write string based content to the writer.
     *
     * @param content the content to write
     * @throws IOException is thrown if content can't be written
     */
    public abstract void write(String content) throws IOException;

    /**
     * Closes encapsulated writer.
     *
     * @throws IOException is thrown if writer can't be closed
     */
    public abstract void close() throws IOException;

}
