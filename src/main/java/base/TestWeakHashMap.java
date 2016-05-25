package base;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author hzmawenjun .
 */
public class TestWeakHashMap {
    static class KeyHolder {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("i am old of key and will be gc");
            super.finalize();
        }
    }

    static class ValueHolder {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("i am old of value and will be gc");
            super.finalize();
        }
    }

    public static void main(String[] args) {
        Map<KeyHolder,ValueHolder> weakHashMap = new WeakHashMap<KeyHolder, ValueHolder>();
        KeyHolder keyHolder = new KeyHolder();
        ValueHolder valueHolder = new ValueHolder();
        weakHashMap.put(keyHolder,valueHolder);

        while (true) {
            for (KeyHolder key : weakHashMap.keySet()) {
                System.out.println("key="+keyHolder +" value="+weakHashMap.get(key));
            }

            try {

                Thread.sleep(1000);
                System.out.println("sleep ....");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            keyHolder = null;

            System.gc();
        }
    }
}
