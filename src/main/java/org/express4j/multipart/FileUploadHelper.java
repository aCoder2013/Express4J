package org.express4j.multipart;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.express4j.core.Express4JConfig;
import org.express4j.http.RequestParam;
import org.express4j.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Song on 2015/12/8.
 */
public class FileUploadHelper {


    private static Logger logger = LoggerFactory.getLogger(FileUploadHelper.class);

    /**
     * File Upload Handler
     */
    private static ServletFileUpload fileUpload ;
    /**
     * 默认表单属性编码
     */
    private static final String DEFAULT_FIELD_ENCODING="utf-8";




    /**
     * Init Method
     * @param servletContext
     */
    public static void init(ServletContext servletContext){
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Configure a repository (to ensure a secure temp location is used)
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        fileUpload = new ServletFileUpload(factory);
        long fileMaxSize = Express4JConfig.getMaxFileSize();
        fileUpload.setFileSizeMax(fileMaxSize);
        fileUpload.setHeaderEncoding(DEFAULT_FIELD_ENCODING);
    }

    /**
     * Check that we have a file upload request
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 读取表单中的参数
     * eg:普通文本或文件
     * @param request
     * @return
     */
    public static void createMultipartForm(RequestParam params ,HttpServletRequest request){
        //Parse the request
        try {
            List<FileItem> items = fileUpload.parseRequest(request);
            // Process the uploaded items
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {
                    processFormField(params,item);
                } else {
                    processUploadedFile(params,item);
                }
            }
        } catch (FileUploadException e) {
            logger.error("File Upload Failure :" +e );
            e.printStackTrace();
        }
    }

    /**
     * Process a regular form field
     * @param params
     * @param item
     */
    private static void processFormField(RequestParam params, FileItem item) {
        String name = item.getFieldName();
        String value = null;
        try {
            value = item.getString(DEFAULT_FIELD_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(name)) {
            params.addRegularField(name,value);
        }
    }

    /**
     * Process a file upload
     * @param params
     * @param item
     */
    private static void processUploadedFile(RequestParam params, FileItem item) {
        try {
            MultipartFile file = new MultipartFileImpl().setName(item.getFieldName())
                    .setOriginalFilename(item.getName())
                    .setSize(item.getSize())
                    .setContentType(item.getContentType())
                    .setBytes(item.get())
                    .setInputStream(item.getInputStream());
            params.addFileField(item.getFieldName(),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
