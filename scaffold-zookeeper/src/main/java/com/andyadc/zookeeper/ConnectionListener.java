package com.andyadc.zookeeper;

/**
 * A Listener which is fired when certain connection events happen with respect to the
 * ZooKeeper service.
 *
 * @author andaicheng
 * @version 2017/5/17
 */
public interface ConnectionListener {

    /**
     * * Fired to indicate that the ZooKeeper Session has expired.
     * <p>
     * When this has been called, some data structures (any structure based on ephemeral nodes) may no longer be
     * in a valid state.
     * <p>
     * <p>NOTE: ZooKeeper does NOT fire session expired events until AFTER a reconnect to ZooKeeper occurs.
     * In the event of a total network partition which lasts forever, no expiration event will be fired. This
     * Means that this method may never be called. If understanding that this behavior is necessary, polling with
     * timeout is required. To do this, use {@link com.andyadc.zookeeper.ZkSessionPoller} manually, or as an underlying part
     * of your {@link com.andyadc.zookeeper.ZkSessionManager} instance. {@link com.andyadc.zookeeper.DefaultZkSessionManager} has
     * an implementation which will poll for these session events.
     */
    void expired();

}
