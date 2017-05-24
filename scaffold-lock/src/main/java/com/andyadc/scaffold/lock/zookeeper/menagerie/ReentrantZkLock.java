package com.andyadc.scaffold.lock.zookeeper.menagerie;

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
import java.util.concurrent.locks.Lock;

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

    /**
     * Acquires the lock.
     * <p>
     * If the lock is not available, then the current thread becomes disabled for thread scheduling purposes and
     * lies dormant until the lock as been acquired.
     * <p>
     * Note: If the ZooKeeper session expires while this method is waiting, a {@link RuntimeException} will be thrown.
     *
     * @throws RuntimeException wrapping:
     *                          <ul>
     *                          <li> {@link KeeperException} if the ZooKeeper server
     *                          encounters a problem
     *                          <li> {@link InterruptedException} if there is a communication problem between
     *                          the ZooKeeper client and server
     *                          <li> If the ZooKeeper session expires
     *                          </ul>
     * @inheritDoc
     * @see Lock#lock()
     */
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

    /**
     * Acquires the lock only if it is free at the time of invocation.
     * <p>
     * Note: If the ZooKeeper Session expires while this thread is processing, nothing is required to happen.
     *
     * @return true if the lock has been acquired, false otherwise
     * @throws RuntimeException wrapping :
     *                          <ul>
     *                          <li>{@link KeeperException} if there is trouble
     *                          processing a request with the ZooKeeper servers.
     *                          <li> {@link InterruptedException} if there is an error communicating between the ZooKeeper client and servers.
     *                          </ul>
     * @inheritDoc
     */
    @Override
    public final boolean tryLock() throws Exception {
        if (checkReentrancy())
            return true;

        ZooKeeper zk = zkSessionManager.getZooKeeper();
        try {
            String lockNode = zk.create(getBaseLockPath(), emptyNode, privileges, CreateMode.EPHEMERAL_SEQUENTIAL);

            //try to determine its position in the queue
            boolean lockAcquired = tryAcquireDistributed(zk, lockNode, false);
            if (!lockAcquired) {
                //we didn't get the lock, so return false
                zk.delete(lockNode, -1);
                return false;
            } else {
                //we have the lock, so it goes on the queue
                locks.set(new LockHolder(lockNode));
                return true;
            }
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Acquires the lock only if it is free within the given waiting time and the current thread has not been
     * interrupted.
     * <p>
     * Note: If the ZooKeeper Session expires while this thread is waiting, an {@link InterruptedException} will be
     * thrown.
     *
     * @param time the maximum time to wait, in milliseconds
     * @param unit the TimeUnit to use
     * @return true if the lock is acquired before the timeout expires
     * @throws InterruptedException if one of the four following conditions hold:
     *                              <ol>
     *                              <li value="1">The Thread is interrupted upon entry to the method
     *                              <li value="2">Another thread interrupts this thread while it is waiting to acquire the lock
     *                              <li value="3">There is a communication problem between the ZooKeeper client and the ZooKeeper server.
     *                              <li value="4">The ZooKeeper session expires and invalidates this lock.
     *                              </ol>
     * @inheritDoc
     * @see #tryLock(long, TimeUnit)
     */
    @Override
    public final boolean tryLock(long time, TimeUnit unit) throws Exception {
        if (Thread.interrupted())
            throw new InterruptedException();

        if (checkReentrancy())
            return true;

        ZooKeeper zk = zkSessionManager.getZooKeeper();
        //add a connection listener
        setConnectionListener();
        String lockNode;
        try {
            lockNode = zk.create(getBaseLockPath(), emptyNode, privileges, CreateMode.EPHEMERAL_SEQUENTIAL);

            while (true) {
                if (Thread.interrupted()) {
                    zk.delete(lockNode, -1);
                    throw new InterruptedException();
                } else if (broken) {
                    throw new InterruptedException("The ZooKeeper Session expired and invalidated this lock");
                }
                boolean localAcquired = localLock.tryLock(time, unit);
                try {
                    if (!localAcquired) {
                        //delete the lock node and return false
                        zk.delete(lockNode, -1);
                        return false;
                    }
                    //ask ZooKeeper for the lock
                    boolean acquiredLock = tryAcquireDistributed(zk, lockNode, true);

                    if (!acquiredLock) {
                        //we don't have the lock, so we need to wait for our watcher to fire
                        boolean alerted = condition.await(time, unit);
                        if (!alerted) {
                            //we timed out, so delete the node and return false
                            zk.delete(lockNode, -1);
                            return false;
                        }
                    } else {
                        //we have the lock, so return happy
                        locks.set(new LockHolder(lockNode));
                        return true;
                    }
                } finally {
                    localLock.unlock();
                }
            }
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        } finally {
            //don't care about the connection any more
            removeConnectionListener();
        }

    }

    /**
     * Releases the currently held lock.
     *
     * @throws RuntimeException wrapping:
     *                          <ul>
     *                          <li> {@link KeeperException} if the ZooKeeper server fails to process the unlock
     *                          request
     *                          <li> {@link InterruptedException} if the ZooKeeper client and server have trouble communicating
     *                          </ul>
     * @inheritDoc
     */
    @Override
    public final void unlock() throws Exception {
        LockHolder nodeToRemove = locks.get();
        if (nodeToRemove == null)
            throw new IllegalMonitorStateException("Attempting to unlock without first obtaining that lock on this thread");

        int numLocks = nodeToRemove.decrementLock();
        if (numLocks == 0) {
            locks.remove();
            try {
                ZkUtils.safeDelete(zkSessionManager.getZooKeeper(), nodeToRemove.lockNode(), -1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
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
