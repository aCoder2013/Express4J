package org.express4j.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Song on 2015/12/7.
 */
public class ClassUtils {


    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    private ClassUtils() {
    }

    /**
     * 获取当前线程的类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static HashSet<Class<?>> getClassSet() {
        HashSet<Class<?>> classHashSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources("");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if (protocol.equals("file")) {
                    handleRegularFile(classHashSet, "", url.getPath());
                } else if (protocol.equals("jar")) {
                    handleJarFile(classHashSet, url);
                }
            }
        } catch (IOException e) {
            logger.error("Load Resource Failure");
            e.printStackTrace();
        }
        return classHashSet;
    }
    /**
     * 处理JAR文件
     * 将所有的.class文件提娜佳
     * @param classHashSet
     * @param url
     * @throws IOException
     */
    private static void handleJarFile(HashSet<Class<?>> classHashSet, URL url) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        while(jarURLConnection!=null){
            JarFile jarFile = jarURLConnection.getJarFile();
            while (jarFile!=null){
                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                while(jarEntryEnumeration.hasMoreElements()){
                    JarEntry entry = jarEntryEnumeration.nextElement();
                    String jarEntityName = entry.getName();
                    if(jarEntityName.endsWith(".class")){
                        String className = jarEntityName.substring(0,jarEntityName.lastIndexOf(".class")).replace("/",".");
                        classHashSet.add(loadClass(className, false));
                    }
                }
            }
        }
    }

    /**
     * 遍历packageName，将所有的.class文件添加到集合中
     *
     * @param classHashSet
     * @param packageName
     * @param path
     */
    static void handleRegularFile(HashSet<Class<?>> classHashSet, String packageName, String path) {
        final File[] files = new File(path).listFiles(pathname -> pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class")));
        for (File temp : files) {
            if (temp.isDirectory()) {
                if (StringUtils.isEmpty(packageName)) {
                    handleRegularFile(classHashSet, temp.getName(), path + "/" + temp.getName());
                } else {
                    handleRegularFile(classHashSet, packageName + "/" + temp.getName(), path + "/" + temp.getName());
                }
            } else if (temp.isFile()) {
                String className = null;
                className = temp.getName().substring(0, temp.getName().indexOf(".class"));
                classHashSet.add(loadClass((packageName + "/" + className).replaceAll("/", "."), false));
            }
        }
    }

    /**
     * 根据类名称加载类
     * 根据isInitiallized的值决定是否初始化
     *
     * @param className
     * @param isInitiallized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitiallized) {
        Class cls = null;
        try {
            cls = Class.forName(className, isInitiallized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("Load Class Failure :" + className, e);
            e.printStackTrace();
        }
        return cls;
    }

    /**
     * 获取ClassPath中指定名称资源的路径
     * @param name the name of resource
     * @return the path of resource
     */
    public static String getResourcePath(String name){
       return Thread.currentThread()
               .getContextClassLoader()
               .getResource(name)
               .getPath();
    }

    /**
     * 得到给定路径的URL
     * @param path
     * @return
     */
    public static URL  getResourceUrl(String path){
        return Thread.currentThread().getContextClassLoader()
                .getResource(path);
    }
}
