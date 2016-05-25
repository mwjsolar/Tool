package delegete.jdkagent;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by hzmawenjun on 2016/3/29.
 */
public class Proxy {
    public static void main (String[] args) {
        LogInvocationHandler logInvocationHandler = new LogInvocationHandler(new TestLog());
        Log logProxy = (Log) logInvocationHandler.getProxy();
        logProxy.log();
        logProxy.logParam("111");
        generateProxy(TestLog.class,"GenProxya");
    }


    public static void generateProxy(Class proxyClass,String proxyName) {
        byte[] proxyBytes = ProxyGenerator.generateProxyClass(proxyName,proxyClass.getInterfaces());
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("d:/"+proxyName+".class"));
            outputStream.write(proxyBytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
