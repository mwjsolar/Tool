package delegete.jdkagent;

/**
 * Created by hzmawenjun on 2016/3/29.
 */
public class TestLog implements Log {
    @Override
    public void log() {
        System.out.println("log records");
    }

    @Override
    public void logParam(String params) {
        System.out.println("log params records");

    }
}
