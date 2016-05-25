package delegete.cglibagent;

import delegete.javaagent.Agent;

/**
 * Created by hzmawenjun on 2016/3/30.
 */
public class Log<T> {
    @Agent
    public  T log(T t) {
        System.out.println("test log " + t);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }
}
