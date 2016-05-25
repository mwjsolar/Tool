package zk.lock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * zookeeper的分布式锁
 *
 * @author hzmawenjun.
 */
public class ZkLock implements Lock {
    private final static Logger log = LoggerFactory.getLogger(ZkLock.class);

    private final Object lockObject = new Object();

    private final ZooKeeper zooKeeper;

    private final String lockName;

    private final String appName;

    private final String LOCK_DIR_PATH = "/lock";

    private final String OPERATOR = "/";

    private String currentPath = "/";

    public ZkLock(ZooKeeper zooKeeper,
                  String appName,
                  String lockName) {
        this.zooKeeper = zooKeeper;
        this.lockName = lockName;
        this.appName = appName;

        Assert.notNull(zooKeeper, "zookeeper can not be null");
        Assert.hasText(lockName, "lockName can not be null");
        Assert.hasText(appName, "appName can not be null");
        try {
            //check znode of appName
            if (!checkExist(LOCK_DIR_PATH + OPERATOR +appName)) {
                if (!checkExist(LOCK_DIR_PATH))
                    zooKeeper.create(LOCK_DIR_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                zooKeeper.create(LOCK_DIR_PATH + OPERATOR +appName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() throws KeeperException, InterruptedException {
        synchronized (lockObject) {
            StringBuffer lockPath = new StringBuffer();
            lockPath.append(LOCK_DIR_PATH)
                    .append(OPERATOR)
                    .append(appName)
                    .append(OPERATOR)
                    .append(lockName);
            if (!checkExist(lockPath.toString())) { //锁住
                zooKeeper.create(lockPath.toString(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                return;
            }

            List<String> childList = zooKeeper.getChildren(lockPath.toString(), new LockWatch());

            currentPath = zooKeeper.create(lockPath + OPERATOR + "lock-", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);


            if (CollectionUtils.isEmpty(childList)) {
                return;
            }

            //对zk  lock排序
            sortZkLock(childList);
            String minPath = childList.get(0);

            Stat stat= zooKeeper.exists(lockPath
                            .append(OPERATOR)
                            .append(minPath).toString(), new LockWatch());

            if (stat != null) {
                System.out.println("lock [lock=" +lockObject+"] " + currentPath + " wait to " +minPath);
                lockObject.wait();
            }

            System.out.println(currentPath + " has be locked");
        }
    }

    private boolean checkExist(String path) {
        try {
            Stat stat = zooKeeper.exists(path, null);
            if (stat != null)
                return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected void sortZkLock(List<String> lockList) {
        Collections.sort(lockList,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.compareToIgnoreCase(o2) < 0)
                    return 1;
                else if (o1.compareToIgnoreCase(o2) > 0)
                    return -1;
                else
                    return 0;

            }
        });
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public void unlock() throws KeeperException, InterruptedException {
        if (StringUtils.isNotBlank(currentPath)) {
            System.out.println("release lock:" + currentPath);
            zooKeeper.delete(currentPath, -1);
        }
    }

    class LockWatch implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            if (Event.EventType.NodeDeleted == event.getType()) {
                System.out.println("notify lock "+lockObject);
                synchronized (lockObject) {
                    lockObject.notifyAll();
                }
                List<Integer> integerList = new ArrayList<Integer>();
                integerList.toArray(new Integer[0]);
            }
        }
    }


}
