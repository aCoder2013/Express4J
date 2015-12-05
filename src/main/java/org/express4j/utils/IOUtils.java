package org.express4j.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Song on 2015/12/5.
 */
public class IOUtils {

    private static final int EOF = -1;

    /**
     * The default buffer size
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * @param inputStream
     * @return the contents of an <code>InputStream</code> as a <code>byte[]</code>
     */
    public static byte[] toByteArray(InputStream inputStream){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            long count = 0;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (EOF != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
