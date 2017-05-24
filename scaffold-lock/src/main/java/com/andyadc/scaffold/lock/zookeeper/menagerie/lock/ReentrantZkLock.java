package com.andyadc.scaffold.lock.zookeeper.menagerie.lock;

import com.andyadc.scaffold.lock.DLock;
import com.andyadc.zookeeper.ZkPrimitive;
import com.andyadc.zookeeper.ZkSessionManager;
import com.andyadc.zookeeper.ZkUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
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

            while (true) {
                if (broken)
                    throw new RuntimeException("ZooKeeper Session has expired, invalidating this lock object");

                localLock.unlock();
                try {
                    //ask ZooKeeper for the lock
                    boolean acquiredLock = tryAcquireDistributed(zk, lockNode, true);
                    if (!acquiredLock) {
                        //we don't have the lock, so we need to wait for our watcher to fire
                        //this method is not interruptible, so need to wait appropriately
                        condition.awaitUninterruptibly();
                    } else {
                        //we have the lock, so return happy
                        locks.set(new LockHolder(lockNode));
                        return;
                    }
                } finally {
                    localLock.unlock();
                }
            }
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //we no longer care about having a ConnectionListener here
            removeConnectionListener();
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
     * Asks ZooKeeper for a lock of a given type.
     * <p>
     * When this method has completed, either the state of the ZooKeeper server is such that this party now
     * holds the lock of the correct type, or, if the {@code watch} parameter is true, any and all necessary
     * Watcher elements have been set.
     * <p>
     * Classes which override this method MUST adhere to the requested watch rule, or else the semantics
     * of the lock interface may be broken. That is, if the {@code watch} parameter is true, then a watch
     * needs to have been set by the end of this method.
     * <p>
     * It is recommended that classes which override this method also override {@link #getBaseLockPath()} and
     * {@link #getLockPrefix()} as well.
     *
     * @param zk       the ZooKeeper client to use
     * @param lockNode the node to lock on
     * @param watch    whether or not to watch other nodes if the lock is behind someone else
     * @return true if the lock has been acquired, false otherwise
     * @throws KeeperException      if Something bad happens on the ZooKeeper server
     * @throws InterruptedException if communication between ZooKeeper and the client fail in some way
     */
    protected boolean tryAcquireDistributed(ZooKeeper zk, String lockNode, boolean watch) throws KeeperException, InterruptedException {
        List<String> locks = ZkUtils.filterByPrefix(zk.getChildren(baseNode, false), getLockPrefix());
        ZkUtils.sortBySequence(locks, lockDelimiter);

        String myNodeName = lockNode.substring(lockNode.lastIndexOf('/') + 1);
        int myPos = locks.indexOf(myNodeName);

        int nextNodePos = myPos - 1;

        while (nextNodePos >= 0) {
            Stat stat;
            if (watch)
                stat = zk.exists(baseNode + "/" + locks.get(nextNodePos), signalWatcher);
            else
                stat = zk.exists(baseNode + "/" + locks.get(nextNodePos), false);

            if (stat != null)
                //there is a node which already has the lock, so we need to wait for notification that that
                //node is gone
                return false;
            else
                nextNodePos--;
        }

        return true;
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
