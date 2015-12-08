package org.express4j.http;

import org.express4j.multipart.MultipartFile;

/**
 * Created by Song on 2015/12/8.
 */
public class FileParam {

    private String name;

    private MultipartFile file;


    public FileParam() {
    }

    public FileParam(String name, MultipartFile file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
