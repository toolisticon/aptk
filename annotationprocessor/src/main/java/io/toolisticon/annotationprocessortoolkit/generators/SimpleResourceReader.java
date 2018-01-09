package io.toolisticon.annotationprocessortoolkit.generators;

import javax.tools.FileObject;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Simple helper class to open and read a resource file.
 */
public class SimpleResourceReader {

    private final FileObject fileObject;
    private final BufferedReader foReader;

    public SimpleResourceReader(FileObject fileObject) throws IOException {
        this.fileObject = fileObject;
        this.foReader = new BufferedReader(fileObject.openReader(true));
    }

    public String readLine() throws IOException {
        return this.foReader.readLine();
    }

    public void close() throws IOException{
        this.foReader.close();
    }


}
