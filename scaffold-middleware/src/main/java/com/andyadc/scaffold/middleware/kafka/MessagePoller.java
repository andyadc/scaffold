package com.andyadc.scaffold.middleware.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author andy.an
 * @since 2017/8/15
 */
public class MessagePoller implements Runnable, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(MessagePoller.class);

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void run() {

    }

    @Override
    public void destroy() throws Exception {

    }
}
