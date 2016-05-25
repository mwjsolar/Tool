package delegete.cglibagent;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by hzmawenjun on 2016/3/30.
 */
public class LogInterceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("intercept before log");
        methodProxy.invokeSuper(o,args);
        System.out.println("intercept after log");
        return null;
    }
}
