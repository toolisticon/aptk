package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Forwarding file manager to be able to test for generated sources and resources
 */
public class CompileTestFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {


    public class FileObjectCache<T> {

        final Map<URI, T> fileObjectCache = new HashMap<URI, T>();

        public boolean contains(URI uri) {
            return fileObjectCache.containsKey(uri);
        }

        public T getFileObject(URI uri) {
            return fileObjectCache.get(uri);
        }

        public void addFileObject(URI uri, T fileObject) {
            fileObjectCache.put(uri, fileObject);
        }

        public Collection<T> getEntries() {
            return fileObjectCache.values();
        }

    }


    final FileObjectCache<JavaFileObject> generatedJavaFileObjectCache = new FileObjectCache<JavaFileObject>();
    final FileObjectCache<FileObject> generatedFileObjectsCache = new FileObjectCache<FileObject>();


    public CompileTestFileManager(StandardJavaFileManager standardJavaFileManager) {
        super(standardJavaFileManager);
    }

    @Override
    public boolean isSameFile(FileObject a, FileObject b) {
        return a.toUri().equals(b.toUri());
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {

        JavaFileObject result = new InMemoryOutputJavaFileObject(uriForJavaFileObject(location, className, kind), kind);
        generatedJavaFileObjectCache.addFileObject(result.toUri(), result);
        return result;

    }

    @Override
    public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
        JavaFileObject result = new InMemoryOutputJavaFileObject(uriForFileObject(location, packageName, relativeName), JavaFileObject.Kind.OTHER);
        generatedFileObjectsCache.addFileObject(result.toUri(), result);
        return result;
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
        if (location.isOutputLocation()) {
            URI uri = uriForJavaFileObject(location, className, kind);
            if (generatedJavaFileObjectCache.contains(uri)) {
                return generatedJavaFileObjectCache.getFileObject(uri);
            } else {
                throw new FileNotFoundException("Can't find JavaFileObject for uri:" + uri.toString());
            }
        }
        return super.getJavaFileForInput(location, className, kind);
    }

    @Override
    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        if (location.isOutputLocation()) {
            URI uri = uriForFileObject(location, packageName, relativeName);
            if (generatedFileObjectsCache.contains(uri)) {
                return generatedFileObjectsCache.getFileObject(uri);
            } else {
                throw new FileNotFoundException("Can't find FileObject for uri:" + uri.toString());
            }
        }
        return super.getFileForInput(location, packageName, relativeName);
    }

    public boolean containsGeneratedJavaFileObject(JavaFileObject javaFileObject) {

        try {
            for (JavaFileObject generatedJavaFileObject : generatedJavaFileObjectCache.getEntries()) {


                if (contentEquals(javaFileObject.openInputStream(), generatedJavaFileObject.openInputStream())) {
                    return true;
                }

            }
        } catch (IOException e) {
            // ignore
        }

        return false;
    }

    public boolean containsGeneratedFileObject(FileObject javaFileObject) {

        try {
            for (FileObject generatedFileObject : generatedFileObjectsCache.getEntries()) {


                if (contentEquals(javaFileObject.openInputStream(), generatedFileObject.openInputStream())) {
                    return true;
                }

            }
        } catch (IOException e) {
            // ignore
        }

        return false;
    }


    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }

        int ch = input1.read();
        while (-1 != ch) {
            int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }

        int ch2 = input2.read();
        return (ch2 == -1);
    }


    interface OutputStreamCallback {

        void setContent(byte[] content);

    }

    ;

    private static URI uriForFileObject(Location location, String packageName, String relativeName) {
        StringBuilder uri = new StringBuilder("mem:///").append(location.getName()).append('/');
        if (!packageName.isEmpty()) {
            uri.append(packageName.replace('.', '/')).append('/');
        }
        uri.append(relativeName);
        return URI.create(uri.toString());
    }

    private static URI uriForJavaFileObject(Location location, String className, JavaFileObject.Kind kind) {
        return URI.create(
                "mem:///" + location.getName() + '/' + className.replace('.', '/') + kind.extension);
    }


    public class InMemoryOutputJavaFileObject extends SimpleJavaFileObject implements OutputStreamCallback {

        private byte[] content;

        public InMemoryOutputJavaFileObject(URI uri, Kind kind) {
            super(uri, kind);
        }

        @Override
        public void setContent(byte[] content) {
            this.content = content;
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return new InMemoryOutputStream(this);
        }

        @Override
        public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
            return new InputStreamReader(openInputStream());
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return content != null ? new String(content) : null;
        }

        @Override
        public Writer openWriter() throws IOException {
            return new OutputStreamWriter(openOutputStream());
        }
    }

    /**
     * Does call a callback function on close to set content.
     * Have to check if it is sufficient to pass content on close.
     */
    public static class InMemoryOutputStream extends ByteArrayOutputStream {

        private final OutputStreamCallback outputStreamCallback;

        public InMemoryOutputStream(OutputStreamCallback outputStreamCallback) {
            this.outputStreamCallback = outputStreamCallback;
        }

        @Override
        public void close() throws IOException {

            super.close();
            outputStreamCallback.setContent(this.toByteArray());

        }

        @Override
        public void write(byte[] b) throws IOException {
            super.write(b);
            outputStreamCallback.setContent(this.toByteArray());
        }
    }
}
