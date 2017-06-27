package zk.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

/**
 *
 * Created by hzmawenjun on 17/6/27.
 */
public class ZkTestClient {
    public static String address = "127.0.0.1:8000";

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new RetryOneTime(2000));
        try {
            client.start();
            client.blockUntilConnected();
            client.create().forPath("test");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

//
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        try {
//            final ZooKeeper zooKeeper = new ZooKeeper(address, 500000, new Watcher() {
//                @Override
//                public void process(WatchedEvent watchedEvent) {
//
//                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
//                        System.out.println("touch event " + watchedEvent.getState());
//                        countDownLatch.countDown();
//                    } else if (watchedEvent.getState() ==  Event.KeeperState.Disconnected) {
//                        System.out.println("node has be Disconnected");
//                    } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
//                        System.out.println("node has be NodeDeleted");
//
//                    }
//                }
//            });
//            client.create("/test", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//            System.out.println(new String(zooKeeper.getData("/test",false,null)));
//            countDownLatch.await();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
