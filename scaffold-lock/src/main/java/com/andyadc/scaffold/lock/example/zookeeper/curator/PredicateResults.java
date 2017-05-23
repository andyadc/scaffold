package com.andyadc.scaffold.lock.example.zookeeper.curator;

/**
 * @author andaicheng
 * @version 2017/5/7
 */
public class PredicateResults {

    private final boolean getsTheLock;
    private final String pathToWatch;

    public PredicateResults(String pathToWatch, boolean getsTheLock) {
        this.pathToWatch = pathToWatch;
        this.getsTheLock = getsTheLock;
    }

    public String getPathToWatch() {
        return pathToWatch;
    }

    public boolean getsTheLock() {
        return getsTheLock;
    }
}
