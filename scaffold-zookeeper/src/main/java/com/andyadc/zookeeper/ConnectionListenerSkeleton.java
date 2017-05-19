package com.andyadc.zookeeper;

/**
 * @author andaicheng
 * @version 2017/5/19
 */
public abstract class ConnectionListenerSkeleton implements ConnectionListener {

    @Override
    public void syncConnected() {
        //default no-op
    }

    @Override
    public void expired() {
        //default no-op
    }

    @Override
    public void disconnected() {
        //default no-op
    }
}
