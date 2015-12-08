package org.express4j.multipart;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Song on 2015/12/8.
 */
public interface MultipartFile {

    /**
     * Return the name of the parameter in the multipart form.
     * @return the name of the parameter (never {@code null} or empty)
     */
    String getName();

    /**
     * Return the original filename in the client's filesystem.
     * <p>This may contain path information depending on the browser used,
     * but it typically will not with any other than Opera.
     * @return the original filename, or the empty String if no file
     * has been chosen in the multipart form, or {@code null}
     * if not defined or not available
     */
    String getOriginalFilename();

    /**
     * Return the content type of the file.
     * @return the content type, or {@code null} if not defined
     * (or no file has been chosen in the multipart form)
     */
    String getContentType();

    /**
     * Return whether the uploaded file is empty, that is, either no file has
     * been chosen in the multipart form or the chosen file has no content.
     */
    boolean isEmpty();

    /**
     * Return the size of the file in bytes.
     * @return the size of the file, or 0 if empty
     */
    long getSize();



    /**
     * Return the contents of the file as an array of bytes.
     * @return the contents of the file as bytes, or an empty byte array if empty
     * @throws IOException in case of access errors (if the temporary store fails)
     */
    byte[] getBytes() throws IOException;

    /**
     * Return an InputStream to read the contents of the file from.
     * The user is responsible for closing the stream.
     * @return the contents of the file as stream, or an empty stream if empty
     * @throws IOException in case of access errors (if the temporary store fails)
     */
    InputStream getInputStream() throws IOException;

    /**
     * Convenient Method To Upload File
     * If the parent path doesn't exist,it'll help
     * you create it .
     * @param file
     */
    void uploadTo(File file);
}