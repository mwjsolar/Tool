package delegete.jdkagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hzmawenjun on 2016/3/29.
 */

public class LogInvocationHandler implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Log log;

    public LogInvocationHandler(Log log) {
        this.log = log;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("before record log");
        method.invoke(log,args);
        logger.info("after record log");
        return null;
    }

    public Object getProxy() {
        return java.lang.reflect.Proxy.newProxyInstance(LogInvocationHandler.class.getClassLoader(), new Class[]{Log.class}
                ,this);
    }
}
