package com.andyadc.scaffold.showcase.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadUtils.class);

    private ThreadUtils() {
    }

    public static void sleepOneSecond() {
        sleepSeconds(1);
    }

    /**
     * sleep seconds
     *
     * @param seconds sleep seconds
     */
    public static void sleepSeconds(int seconds) {
        try {
            LOG.debug("Thread {} sleep {} seconds...", Thread.currentThread().getName(), seconds);
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
