package com.andyadc.scaffold.zookeeper;

import org.apache.zookeeper.ZooKeeper;

/**
 * @author andaicheng
 * @version 2017/5/17
 */
public class DefaultZkSessionManager implements ZkSessionManager {

    @Override
    public ZooKeeper getZooKeeper() {
        return null;
    }

    @Override
    public void closeSession() {

    }

    @Override
    public void addConnectionListener(ConnectionListener listener) {

    }

    @Override
    public void removeConnectionListener(ConnectionListener listener) {

    }
}
