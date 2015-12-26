package other;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Song on 2015/12/12.
 */
public class RegTest {


    @Test
    public void nsds(){
        try {
            System.out.println(Class.forName("String"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        String str= "POST /world  controller.HelloRoute.world(id:String,name:String)";
        String target = str.substring(str.indexOf("(")+1,str.lastIndexOf(")"));
        String[] nameAndTypes = target.split(",");
        for(String nameAndType : nameAndTypes ){
            System.out.println(nameAndType);
            String[] paramWrapper = nameAndType.split(":");
            String name = paramWrapper[0];
            String type = paramWrapper[1];
        }
    }

    public void test(){
        String templatePath = "/news/:id/detail";
        String path = "/news/123/detail";
        String[] templatePathList = templatePath.split("/");
        System.out.println(Arrays.toString(templatePathList));
        String[] pathList = path.split("/");
        System.out.println(Arrays.toString(pathList));
        boolean matched = matches(templatePathList, pathList);
        System.out.println(matched);
    }

    private boolean matches(String[] templatePathList, String[] pathList) {
        if(templatePathList.length==pathList.length){
           int length = templatePathList.length;
            int i = 0;
            while(i<length){
                String partPath = pathList[i];
                String partTemplatePath = templatePathList[i];
                if(!partPath.equals(partTemplatePath)){
                    if(partTemplatePath.startsWith(":")){
                       continue;
                    }
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }


    public void test1(){
        String path  = "/news/:id/detail";
        int colonIndex = path.indexOf(":");
        System.out.println(colonIndex);
        int salash = StringUtils.indexOf(path,'/',colonIndex);
        System.out.println(salash);
        path = path.replace(path.substring(colonIndex,salash),"(\\d)+?");
        System.out.println(path);
    }
}
