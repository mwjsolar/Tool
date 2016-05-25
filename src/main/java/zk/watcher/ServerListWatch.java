package zk.watcher;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author hzmawenjun .
 */
public class ServerListWatch {
    private static final Logger log = LoggerFactory.getLogger(ServerListWatch.class);

    private  ZooKeeper zooKeeper;

    private static final String SERVER_LIST_DIR = "/zk/serverList";

    private static final String OPERATOR = "/";

    private List<String> serverList = new ArrayList<String>();

    public void connectZk(String addressList) {
        if (StringUtils.isBlank(addressList))
            return;

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            zooKeeper = new ZooKeeper(addressList, 500000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    //keeper state
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        countDownLatch.countDown();
                    } else if (watchedEvent.getState() == Event.KeeperState.Disconnected) {
                        log.info("node has be Disconnected");
                    }

                    //event type
                    if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                        log.info("node has be NodeDeleted");
                    } else if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
                        updateServerList();
                    }
                }
            });
        } catch (Exception e) {
            log.error("zk connect error",e);
            return;
        }

        updateServerList();
    }

    protected void updateServerList() {
        try {
            List<String> childList = zooKeeper.getChildren(SERVER_LIST_DIR, true);
            if (CollectionUtils.isEmpty(childList))
                return;

            List<String> newServerList = new ArrayList<String>(childList.size());
            for (String childName : childList) {
                byte[] data = zooKeeper.getData(SERVER_LIST_DIR + OPERATOR +childName,null,null);
                newServerList.add(new String(data));
            }

            serverList = newServerList;
            log.info("server list update [{}]",serverList);
        } catch (Exception e) {
            log.error("update server list error", e);
        }
    }

    public List<String> getServerList() {
        return serverList;
    }

}
