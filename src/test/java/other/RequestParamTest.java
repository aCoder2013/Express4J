package other;

import org.express4j.http.RequestParam;
import org.express4j.multipart.MultipartFileImpl;
import org.junit.Test;

/**
 * Created by Song on 2015/12/8.
 */
public class RequestParamTest {

    @Test
    public void test(){
        RequestParam requestParam = new RequestParam();
        requestParam.addFileField("img",new MultipartFileImpl().setName("img"));
        requestParam.addFileField("img",new MultipartFileImpl().setName("img"));
        requestParam.addFileField("img1",new MultipartFileImpl().setName("img"));
        requestParam.getFiles("img1").stream()
        .forEach(System.out::println);
    }
}
