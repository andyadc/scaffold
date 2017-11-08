package com.andyadc.scaffold.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author andy.an
 * @since 2017/11/8
 */
public class Recv {

    private static final Logger LOGGER = LoggerFactory.getLogger(Recv.class);

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        LOGGER.info(" [*] Waiting for messages. ");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                LOGGER.info(" [x] Received '" + message + "'");
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
