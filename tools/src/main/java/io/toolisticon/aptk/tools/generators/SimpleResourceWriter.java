package io.toolisticon.aptk.tools.generators;

import javax.tools.FileObject;
import java.io.IOException;

/**
 * Simple helper class to create a file and to add some content by appending.
 * This can be very useful for the creation of text based resource files.
 */

public class SimpleResourceWriter extends FileObjectSimpleWriter<FileObject> {


    public SimpleResourceWriter(FileObject fileObject) throws IOException {
        super(fileObject);
    }


}