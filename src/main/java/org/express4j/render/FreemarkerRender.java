package org.express4j.render;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.express4j.core.Express4JConfig;
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

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerRender.class);

    /**
     * 默认模板编码
     */
    private static final String DEFAULT_TEMPLATE_CHARSET = "UTF-8";
    /**
     * Freemarker配置
     */
    private static Configuration configuration;



    static {
        /**
         * 在整个应用的生命周期中，只初始化一次。
        */
        configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(
                    new File(Express4JConfig.getTemplatesPath()));
            configuration.setObjectWrapper(new DefaultObjectWrapper());
        } catch (IOException e) {
            logger.error("Template Directory Loading Failure", e);
            e.printStackTrace();
        }
    }


    /**
     *  用给定的模型渲染模板，写到给定的输出中
     * to the supplied {@link Writer}.
     * @param name The name of the template. Can't be {@code null}.
     * @param models the holder of the variables visible from the template (name-value pairs)
     * @param output The {@link Writer} where the output of the template will go.
     */
    public static void render(String name,Map<String,Object> models,Writer output){
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
