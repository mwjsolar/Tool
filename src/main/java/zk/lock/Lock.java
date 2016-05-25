package zk.lock;

import org.apache.zookeeper.KeeperException;

/**
 * @author hzmawenjun .
 */
public interface Lock {
    /**
     * lock
     * @throws KeeperException
     * @throws InterruptedException
     */
    void lock() throws KeeperException, InterruptedException;

    /**
     * try to lock
     * @return
     */
    boolean tryLock();

    /**
     * unlock
     */
    void unlock() throws KeeperException, InterruptedException;
}
