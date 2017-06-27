package zk.lock;

import org.apache.zookeeper.KeeperException;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 实现aqs同步器
 * Created by mwjsolar on 17/3/13.
 */
public class SyncAQS {
    private static ExeculdeSync sync = new ExeculdeSync();
    public static class ExeculdeSync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }
    }

    static class  SelfLock implements Lock {
        @Override
        public void lock() throws KeeperException, InterruptedException {
            sync.acquire(1);
        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public void unlock() throws KeeperException, InterruptedException {
            sync.release(1);
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        //test
        Lock execuldeSync = new SelfLock();
        execuldeSync.lock();
        System.out.println("start business 1");
        System.out.println("start business 1");
        execuldeSync.unlock();

        Thread businessThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Lock execuldeSync = new SelfLock();
                try {
                    execuldeSync.lock();
                    System.out.println("start business 2");
                    System.out.println("start business 2");
                    execuldeSync.unlock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        businessThread.start();

        Thread businessThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Lock execuldeSync = new SelfLock();
                try {
                    execuldeSync.lock();
                    System.out.println("start business 3");
                    System.out.println("start business 3");
                    execuldeSync.unlock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        businessThread2.start();

        Thread businessThread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                Lock execuldeSync = new SelfLock();
                try {
                    execuldeSync.lock();
                    System.out.println("start business 3");
                    System.out.println("start business 3");
                    execuldeSync.unlock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        businessThread3.start();
    }
}
