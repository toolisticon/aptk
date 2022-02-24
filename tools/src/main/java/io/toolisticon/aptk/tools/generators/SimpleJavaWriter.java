package io.toolisticon.aptk.tools.generators;

import javax.tools.JavaFileObject;
import java.io.IOException;


/**
 * A Simple java writer.
 */
public class SimpleJavaWriter extends FileObjectSimpleWriter<JavaFileObject> {


    public SimpleJavaWriter(JavaFileObject fileObject) throws IOException {
        super(fileObject);
    }


}
