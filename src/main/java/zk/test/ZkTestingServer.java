package zk.test;

import org.apache.curator.test.TestingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 测试testing server
 * Created by hzmawenjun on 17/6/27.
 */
public class ZkTestingServer {

    private static final Logger logger = LoggerFactory.getLogger(ZkTestingServer.class);

    /**
     * testing server
     */
    private static TestingServer testingServer;

    private final static int PORT = 8000;

    private final static String TEMP_DIRECTORY = "/Users/mwjsolar";

    private static AtomicBoolean started = new AtomicBoolean(false);

    public static void start() {
        if (!started.compareAndSet(false, true)) {
            logger.error("zk testing server has be started");
            return;
        }

        try {
            if (testingServer == null) {
                testingServer = new TestingServer(PORT, new File(TEMP_DIRECTORY));
            }

            testingServer.start();
        } catch (Exception e) {
            logger.error("zk test server start error", e);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (testingServer != null) {
                        try {
                            testingServer.stop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
