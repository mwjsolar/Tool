package base;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hzmawenjun on 2016/5/15.
 */
public class TestTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask1(),7000);

        timer.schedule(new TimeTask2(),5000);

        timer.schedule(new TimeTask2(),80000);

    }

    static class TimerTask1 extends TimerTask {
        @Override
        public void run() {
            System.out.println("this is task1");
        }
    }

    static class TimeTask2 extends TimerTask {
        @Override
        public void run() {
            System.out.println("this is task2");
        }
    }
}
