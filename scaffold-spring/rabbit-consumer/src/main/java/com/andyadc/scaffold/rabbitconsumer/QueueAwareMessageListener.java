package com.andyadc.scaffold.rabbitconsumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * @author andaicheng
 * @since 2017/11/26
 */
public class QueueAwareMessageListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("--------ChannelAwareMessageListener---------");
        System.out.println("message: " + message);

        // manual ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
