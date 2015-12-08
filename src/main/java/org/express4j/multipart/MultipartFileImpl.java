package org.express4j.multipart;

import org.express4j.utils.FileUtils;
import org.express4j.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Song on 2015/12/8.
 */
public class MultipartFileImpl implements MultipartFile {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * the name of the parameter in the multipart form.
     */
    private String name;
    /**
     * the original filename in the client's filesystem.
     */
    private String originalFilename;

    private String ContentType;

    private long size;

    private byte[] bytes;

    private InputStream inputStream;


    public MultipartFileImpl setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFileImpl setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }

    public MultipartFileImpl setContentType(String contentType) {
        ContentType = contentType;
        return this;
    }

    public MultipartFileImpl setSize(long size) {
        this.size = size;
        return this;
    }

    public MultipartFileImpl setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public MultipartFileImpl setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.ContentType;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.bytes;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return this.inputStream;
    }

    /**
     * 上传到指定路径
     * path+fileName
     * @param file
     */
    public void uploadTo(File file) {
        FileUtils.createParentPath(file.getPath());
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            StreamUtils.copyStream(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            logger.error("File Not Found !",e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public String toString() {
        return "MultipartFileImpl{" +
                "name='" + name + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", ContentType='" + ContentType + '\'' +
                ", size=" + size +
                ", inputStream=" + inputStream +
                '}';
    }
}
