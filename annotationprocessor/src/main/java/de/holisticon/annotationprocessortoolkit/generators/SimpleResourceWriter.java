package de.holisticon.annotationprocessortoolkit.generators;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.Writer;

/**
 * Simple helper class to create a file and to add some content by appending.
 * This can be very useful for the creation of text based resource files.
 */

public class SimpleResourceWriter {

    private final FileObject fileObject;
    private final Writer foWriter;

    public SimpleResourceWriter(FileObject fileObject) throws IOException {
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

    public void write(String content) throws IOException {
        foWriter.write(content);
        foWriter.flush();
    }

    public void close() throws IOException {
        foWriter.flush();
        foWriter.close();
    }

}
