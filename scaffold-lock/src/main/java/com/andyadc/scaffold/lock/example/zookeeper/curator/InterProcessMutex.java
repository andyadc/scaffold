package com.andyadc.scaffold.lock.example.zookeeper.curator;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author andaicheng
 * @version 2017/5/7
 */
public class InterProcessMutex implements InterProcessLock {

    private static class LockData {
        final Thread owningThread;
        final String lockPath;
        final AtomicInteger lockCount = new AtomicInteger(1);

        private LockData(Thread owningThread, String lockPath) {
            this.owningThread = owningThread;
            this.lockPath = lockPath;
        }
    }

    private static final String LOCK_NAME = "lock-";

    @Override
    public void acquire() throws Exception {

    }

    @Override
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        return false;
    }

    @Override
    public void release() throws Exception {

    }

    @Override
    public boolean isAcquiredInThisProcess() {
        return false;
    }
}
