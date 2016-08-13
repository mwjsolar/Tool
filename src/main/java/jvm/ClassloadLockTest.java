package jvm;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by mwjsolar on 16/8/7.
 */
public class ClassloadLockTest {
    static class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    DocumentBuilder b = DocumentBuilderFactory.newInstance()
                            .newDocumentBuilder();

                } catch (Exception e) {// Do not do this at home, log all
                    // exceptions
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            new Worker().start();
        }
    }
}
