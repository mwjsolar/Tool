package delegete.javaagent;

import delegete.cglibagent.Log;
import delegete.cglibagent.LogInterceptor;
import net.sf.cglib.proxy.Enhancer;

/**
 * Created by hzmawenjun on 2016/3/30.
 */
public class TestLog {
    public static void main(String[] args) {
        Log log = new Log();
        log.log("ssss");
    }
}
