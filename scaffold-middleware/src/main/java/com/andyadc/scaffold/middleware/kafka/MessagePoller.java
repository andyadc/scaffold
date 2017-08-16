package com.andyadc.scaffold.middleware.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author andy.an
 * @since 2017/8/15
 */
public class MessagePoller implements Runnable, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(MessagePoller.class);

    private static AtomicLong tid = new AtomicLong(0);

    private volatile boolean running = true;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void run() {

        Thread pollerThread = new Thread(this);
        pollerThread.setName("MessagePoller-" + tid.getAndIncrement());
        pollerThread.start();
    }

    private static Properties getDefaultConfig() {
        Properties props = new Properties();
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public void destroy() throws Exception {
        this.running = false;
    }
}
