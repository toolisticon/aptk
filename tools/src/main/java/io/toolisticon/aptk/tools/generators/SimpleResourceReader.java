package io.toolisticon.aptk.tools.generators;

import javax.tools.FileObject;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    /**
     * Read resource manually line by line.
     *
     * @return the current line from reader
     * @throws IOException
     */
    public String readLine() throws IOException {
        return this.foReader.readLine();
    }

    /**
     * Reads the whole file or remaining resource to a String.
     *
     * @return the content of the resource file starting from the current reader position
     * @throws IOException
     */
    public String readAsString() throws IOException {

        char[] buffer = new char[10000];
        CharArrayWriter writer = new CharArrayWriter();

        int line = 0;
        // read bytes from stream, and store them in buffer
        while ((line = foReader.read(buffer)) != -1) {
            // Writes bytes from byte array (buffer) into output stream.
            writer.write(buffer, 0, line);
        }
        foReader.close();
        writer.flush();
        writer.close();

        return writer.toString();

    }

    /**
     * Reads the whole resource into a list of lines.
     *
     * @return the content as a line by line array of the resource file starting from the current reader position
     * @throws IOException
     */
    public List<String> readAsLines() throws IOException {
        return readAsLines(false);
    }


    /**
     * Reads the whole resource into a list of lines.
     *
     * @param trimLines defines wether or not lines should be trimmed
     * @return the content as a line by line array of the resource file starting from the current reader position
     * @throws IOException
     */
    public List<String> readAsLines(boolean trimLines) throws IOException {

        List<String> result = new ArrayList<>();

        try {

            String line = foReader.readLine();
            while (line != null) {

                result.add(trimLines ? line.trim() : line);

                // read next line
                line = foReader.readLine();

            }

        } finally {
            foReader.close();
        }


        return result;
    }

    /**
     * Reads the resource into Properties.
     *
     * @return
     * @throws IOException
     */
    public Properties readAsProperties() throws IOException {

        Properties properties = new Properties();
        try {
            properties.load(foReader);
        } finally {
            foReader.close();
        }

        return properties;
    }

    /**
     * Closes the underlying reader.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.foReader.close();
    }


}
