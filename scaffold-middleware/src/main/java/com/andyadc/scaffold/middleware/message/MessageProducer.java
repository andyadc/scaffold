package com.andyadc.scaffold.middleware.message;

/**
 * @author andy.an
 * @since 2017/8/15
 */
public interface MessageProducer {

    void send(String topic, Message<?> message);

    void send(String topic, String key, Message<?> message);
}
