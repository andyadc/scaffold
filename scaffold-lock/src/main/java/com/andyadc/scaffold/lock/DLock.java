package com.andyadc.scaffold.lock;

import java.util.concurrent.TimeUnit;

/**
 * {@code DLock} is the abbreviation of distributed lock.
 * <p>
 * A distributed lock is a tool for controlling access to a shared resource by multiple threads or processes.
 * Commonly, a distributed lock provides exclusive access to a shared resource:
 * only one thread at a time can acquire the lock and all access to the shared resource requires that the lock be acquired first.
 *
 * @author andaicheng
 * @version 2017/4/28
 */
public interface DLock {

    /**
     * Acquires the lock.
     * <p>
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until the
     * lock has been acquired.
     *
     * @throws Exception distributed errors, connection interruptions
     */
    void lock() throws Exception;

    /**
     * Acquires the lock only if it is free at the time of invocation.
     * <p>
     * <p>Acquires the lock if it is available and returns immediately
     * with the value {@code true}.
     * If the lock is not available then this method will return
     * immediately with the value {@code false}.
     * <p>
     * <p>This usage ensures that the lock is unlocked if it was acquired, and
     * doesn't try to unlock if the lock was not acquired.
     *
     * @return {@code true} if the lock was acquired and
     * {@code false} otherwise
     * @throws Exception distributed errors, connection interruptions
     */
    boolean tryLock() throws Exception;

    /**
     * Acquires the lock if it is free within the given waiting time
     * <p>
     * <p>If the lock is available this method returns immediately
     * with the value {@code true}.
     * If the lock is not available then
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until one of three things happens:
     *
     * @param time the maximum time to wait for the lock
     * @param unit the time unit of the {@code time} argument
     * @return {@code true} if the lock was acquired and {@code false}
     * if the waiting time elapsed before the lock was acquired
     * @throws Exception distributed errors, connection interruptions
     */
    boolean tryLock(long time, TimeUnit unit) throws Exception;

    /**
     * Releases the lock.
     * <p>
     * <p>make sure you hold the lock, you release lock by invoking {#unlock()}
     *
     * @throws Exception distributed errors, connection interruptions
     */
    void unlock() throws Exception;
}
