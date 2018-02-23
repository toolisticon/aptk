package io.toolisticon.annotationprocessortoolkit.generators;

import javax.tools.JavaFileObject;
import java.io.IOException;


/**
 * A Simple java writer.
 */
public class SimpleJavaWriter extends AbstractSimpleWriter<JavaFileObject> {


    public SimpleJavaWriter(JavaFileObject fileObject) throws IOException {
        super(fileObject);
    }


}
