package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author andaicheng
 * @since 2017/11/15
 */
public class QueueMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("--------->> " + new String(message.getBody()));
    }
}
