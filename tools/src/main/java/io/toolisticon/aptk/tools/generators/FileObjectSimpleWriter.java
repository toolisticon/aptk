package io.toolisticon.aptk.tools.generators;

import java.io.IOException;
import java.io.Writer;

import javax.tools.FileObject;

/**
 * Abstract base class for writers of java code and resources.
 */
public class FileObjectSimpleWriter<T extends FileObject> extends SimpleWriter implements AutoCloseable{

    private final T fileObject;
    private final Writer foWriter;

    public FileObjectSimpleWriter(T fileObject) throws IOException {
        this.fileObject = fileObject;
        this.foWriter = fileObject.openWriter();
    }

    /**
     * Appends string content to the writer.
     *
     * @param content the content to append
     * @throws IOException is thrown if content can't be written
     */
    public void append(String content) throws IOException {
        foWriter.append(content);
        foWriter.flush();
    }

    /**
     * Write char array content to the writer.
     *
     * @param buffer the buffer to append
     * @throws IOException is thrown if buffer can't be written
     */
    public void write(char[] buffer) throws IOException {
        foWriter.write(buffer);
        foWriter.flush();
    }



    /**
     * Write string based content to the writer.
     *
     * @param content the content to write
     * @throws IOException is thrown if content can't be written
     */
    public void write(String content) throws IOException {
        foWriter.write(content);
        foWriter.flush();
    }

    /**
     * Closes encapsulated writer.
     *
     * @throws IOException is thrown if writer can't be closed
     */
    public void close() throws IOException {
        foWriter.flush();
        foWriter.close();
    }

}
