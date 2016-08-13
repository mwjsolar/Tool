package protocol.serialize;

import java.io.Serializable;

/**
 * Created by hzmawenjun on 2016/4/17.
 */
public class TestObject implements Serializable {
    private Integer testA = 1;

    private int testB = 2;

    public int getTestA() {
        return testA;
    }

    public void setTestA(int testA) {
        this.testA = testA;
    }
}
