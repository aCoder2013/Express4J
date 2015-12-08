package org.express4j.http;

import org.express4j.exception.ParamConflictException;
import org.express4j.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2015/12/8.
 */
public class RequestParam {

    private Map<String,Object> regularParams = new HashMap<>();

    private List<FileParam> fileParams = new ArrayList<>();

    public void addRegularField(String name,Object value){
        regularParams.put(name,value);
    }

    public void addFileField(String name,MultipartFile file){
        fileParams.add(new FileParam(name, file));
    }

    public Object getRegularField(String name){
        return regularParams.get(name);
    }


    public MultipartFile getFile(String name){
        List<MultipartFile> files = getFiles(name);
        if(files.size()>1){
            throw new ParamConflictException("Find more than one parameter with the given name : " + name);
        }
        return files.size()==1?files.get(0):null;
    }

    public List<MultipartFile> getFiles(String name){
        List<MultipartFile> fileList = new ArrayList<>();
        fileParams.stream()
                .filter(fileParam ->fileParam.getName().equals(name))
                .forEach(param->fileList.add(param.getFile()));
        return fileList;
    }

    public Map<String,MultipartFile> getFileMap(String name){
        Map<String ,MultipartFile> fileMap = new HashMap<>();
        fileParams.stream()
                .filter(fileParam ->fileParam.getName().equals(name))
                .forEach(f->fileMap.put(f.getName(),f.getFile()));
        return fileMap;
    }
}
