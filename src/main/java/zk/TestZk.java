package zk;

import org.apache.zookeeper.*;
import zk.lock.Lock;
import zk.lock.ZkLock;
import zk.watcher.ServerListWatch;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hzmawenjun on 2015/12/30.
 */
public class TestZk {
    public static String address = "10.165.124.84:2181";

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            final ZooKeeper zooKeeper = new ZooKeeper(address, 500000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        System.out.println("touch event " + watchedEvent.getState());
                        countDownLatch.countDown();
                    } else if (watchedEvent.getState() ==  Event.KeeperState.Disconnected) {
                        System.out.println("node has be Disconnected");
                    } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                        System.out.println("node has be NodeDeleted");

                    }
                }
            });
            countDownLatch.await();

//            Lock zkLock = new ZkLock(zooKeeper, "zkLock", "test");
//            zkLock.lock();
//
//            LockThread lockthread = new LockThread(zooKeeper);
//            lockthread.start();
//            Thread.sleep(10000);
//            zkLock.unlock();
//            lockthread.join();

            //test server list
            ServerListWatch serverListWatch = new ServerListWatch();
            serverListWatch.connectZk(address);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class LockThread extends Thread {
    private final ZooKeeper zooKeeper;
    LockThread(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    @Override
    public void run() {
        Lock zkLock = new ZkLock(zooKeeper, "zkLock", "test");
        try {
            System.out.println("thread lock");
            zkLock.lock();
            Thread.sleep(3000);
            zkLock.unlock();
            System.out.println("thread unlock");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}