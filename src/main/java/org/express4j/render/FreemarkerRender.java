package org.express4j.render;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by Song on 2015/12/6.
 */
public class FreemarkerRender {

    private  final Logger logger = LoggerFactory.getLogger(FreemarkerRender.class);

    private static final FreemarkerRender INSTANCE = new FreemarkerRender();
    private final String DEFAULT_TEMPLATE_CHARSET = "UTF-8";
    private  Configuration configuration;

    private  String defaultTemplateDir = getClass().getClassLoader().getResource("templates").getPath();

    private boolean initiated = false;

    public static FreemarkerRender getInstance(){
        return INSTANCE;
    }

    public void init() {
        logger.info(defaultTemplateDir);
        /**
         * 在整个应用的生命周期中，这个工作你应该只做一次。
        */
        configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(
                    new File(defaultTemplateDir));
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            initiated = true;
        } catch (IOException e) {
            logger.error("Template Directory Loading Failure", e);
            e.printStackTrace();
        }
    }



    public  void render(String name,Map<String,Object> models,Writer output){
        if(!initiated){
           init();
        }
        try {
            Template template = configuration.getTemplate(name,DEFAULT_TEMPLATE_CHARSET);
            template.process(models,output);
        } catch (IOException e) {
            logger.error("Loading Find Template Failure",e);
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("Process Template Failure",e);
            e.printStackTrace();
        }
    }

}
