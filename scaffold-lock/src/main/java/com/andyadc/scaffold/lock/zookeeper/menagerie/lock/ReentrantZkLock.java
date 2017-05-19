package com.andyadc.scaffold.lock.zookeeper.menagerie.lock;

import com.andyadc.scaffold.lock.DLock;
import com.andyadc.zookeeper.ZkPrimitive;
import com.andyadc.zookeeper.ZkSessionManager;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author andaicheng
 * @version 2017/5/10
 */
public class ReentrantZkLock extends ZkPrimitive implements DLock {

    private static final String lockPrefix = "lock";

    /**
     * A default delimiter to separate a lockPrefix from the sequential elements set by ZooKeeper.
     */
    protected static final char lockDelimiter = '-';

    private final ThreadLocal<LockHolder> locks = new ThreadLocal<>();

    /**
     * Constructs a new Lock on the specified node, using Open ACL privileges.
     *
     * @param baseNode         the node to lock on
     * @param zkSessionManager the session manager to use.
     */
    public ReentrantZkLock(String baseNode, ZkSessionManager zkSessionManager) {
        super(baseNode, zkSessionManager, ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    @Override
    public final void lock() throws Exception {
        if (checkReentrancy()) {
            return;
        }

        //set a connection listener to listen for session expiration
        setConnectionListener();

        ZooKeeper zk = zkSessionManager.getZooKeeper();

        String lockNode;
        try {
            lockNode = zk.create(getBaseLockPath(), emptyNode, privileges, CreateMode.EPHEMERAL_SEQUENTIAL);


        } catch (KeeperException e) {

        } catch (InterruptedException e) {

        }

    }

    @Override
    public final boolean tryLock() throws Exception {
        return checkReentrancy();

    }

    @Override
    public final boolean tryLock(long time, TimeUnit unit) throws Exception {
        return checkReentrancy();

    }

    @Override
    public final void unlock() throws Exception {
        LockHolder nodeToRemove = locks.get();
        if (nodeToRemove == null) {
            throw new IllegalMonitorStateException("Attempting to unlock without first obtaining that lock on this thread");
        }

        int numLocks = nodeToRemove.decrementLock();
        if (numLocks == 0) {
            locks.remove();

            // TODO delete node

        }

    }

    /**
     * Gets the name of the lock which this thread holds.
     * <p>
     * Note: This method will return {@code null} <i>unless</i> the current thread is the lock owner. This method
     * is primarily intended to ease the use of shared lock implementations between threads, and should not be used
     * to manage lock state.
     *
     * @return the name of the lock which this thread owns, or null.
     */
    protected final String getLockName() {
        LockHolder lockHolder = locks.get();
        if (lockHolder == null) return null;
        return lockHolder.lockNode();
    }

    /**
     * Determines whether or not this party owns the lock.
     *
     * @return true if the current party(i.e. Thread and ZooKeeper client) owns the Lock
     */
    public final boolean hasLock() {
        return locks.get() != null;
    }

    /**
     * Gets the prefix to use for locks of this type.
     *
     * @return the prefix to prepend to all nodes created by this lock.
     */
    protected String getLockPrefix() {
        return lockPrefix;
    }

    /**
     * Gets the base path for a lock node of this type.
     *
     * @return the base lock path(all the way up to a delimiter for sequence elements)
     */
    protected String getBaseLockPath() {
        return baseNode + "/" + getLockPrefix() + lockDelimiter;
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    /**
     * Checks whether or not this party is re-entering a lock which it already owns.
     * If this party already owns the lock, this method increments the lock counter and returns true.
     * Otherwise, it return false.
     */
    private boolean checkReentrancy() {
        LockHolder local = locks.get();
        if (local != null) {
            local.incrementLock();
            return true;
        }
        return false;
    }

    /*Holder for information about a specific lock*/
    private static class LockHolder {

        private final String lockNode;
        private final AtomicInteger numLocks = new AtomicInteger(1);

        private LockHolder(String lockNode) {
            this.lockNode = lockNode;
        }

        public void incrementLock() {
            numLocks.incrementAndGet();
        }

        public int decrementLock() {
            return numLocks.decrementAndGet();
        }

        public String lockNode() {
            return lockNode;
        }
    }
}
