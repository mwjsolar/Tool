package delegete.cglibagent;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by hzmawenjun on 2016/3/30.
 */
public class TestLog {
    public static void main(String[] args) {
        Log log = new Log();
        LogInterceptor logInterceptor = new LogInterceptor();
        Enhancer  enhancer = new Enhancer();
        enhancer.setCallback(logInterceptor);
        enhancer.setSuperclass(log.getClass());
        Log logProxy = (Log) enhancer.create();
        logProxy.log(1);
    }
}
