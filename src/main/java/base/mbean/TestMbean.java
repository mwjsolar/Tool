package base.mbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by mwjsolar on 16/5/27.
 */
public class TestMbean implements TestMbeanMBean {

    private static final String NAME = "com.tool.base.test:type=testMbean";

    private static TestMbean mbean = new TestMbean();

    static {
        registerMbean();
    }
    public static void main(String[] args) {
        try {
            Thread.sleep(600000);
            mbean.print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  static void registerMbean() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName objectName = new ObjectName(NAME);
            mBeanServer.registerMBean(mbean,objectName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int testA;

    public void print() {
        System.out.println(testA);
    }

    public int getTestA() {
        return testA;
    }

    public void setTestA(int testA) {
        this.testA = testA;
    }
}
