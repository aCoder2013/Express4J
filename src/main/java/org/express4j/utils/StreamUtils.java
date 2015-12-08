package org.express4j.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Song on 2015/12/8.
 */
public class StreamUtils {

    private static Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    /**
     * 将输入流复制到输出流
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream,OutputStream outputStream){
        int length ;
        byte[] buffer = new byte[4*1024];
        try {
            while ((length=inputStream.read(buffer,0,buffer.length))!=-1){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("Copy Stream Failure",e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                logger.error("Close Stream Failure",e);
                throw new RuntimeException(e);
            }
        }

    }
}
