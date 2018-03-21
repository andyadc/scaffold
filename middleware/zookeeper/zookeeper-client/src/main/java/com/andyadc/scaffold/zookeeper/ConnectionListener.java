package com.andyadc.scaffold.zookeeper;

/**
 * A Listener which is fired when certain connection events happen with respect to the
 * ZooKeeper service.
 *
 * @author andaicheng
 * @version 2017/5/17
 */
public interface ConnectionListener {

    /**
     * Fired to indicated that the ZooKeeper client has reconnected to the ZooKeeper service after having
     * been disconnected, but before the session timeout has expired.
     */
    void syncConnected();

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

    /**
     * Indicates that the connection to ZooKeeper has been lost.
     * <p>
     * <p>Connections to ZooKeeper may be lost due to network partitions or due to ZooKeeper servers failing. In
     * many situations, becoming disconnected is transient, as the client may quickly reconnect to another server
     * in the ensemble. If, however, the client cannot connect to a ZooKeeper server within the specified session
     * timeout, then the ZooKeeper session is terminated by the ensemble, and the client transitions to the expired
     * state. Note, however, that the client is <em>not</em> guaranteed to receive notification of session expiration,
     * only of disconnection. It is therefore recommended that services which are subject to disconnection generally
     * cease operations, or at the very least pause operations, while a connection is re-established.
     */
    void disconnected();
}
