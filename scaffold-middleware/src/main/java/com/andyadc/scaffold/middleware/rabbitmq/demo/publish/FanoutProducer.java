package com.andyadc.scaffold.middleware.rabbitmq.demo.publish;

import com.andyadc.scaffold.middleware.rabbitmq.demo.RabbitConst;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author andy.an
 * @since 2017/8/18
 */
public class FanoutProducer {

    private static final String EXCHANGE_NAME = "fanout_test";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername("messager");
        factory.setPassword("messager123");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        for (int i = 0; i < 10; i++) {
            String message = "this is message " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        }

        channel.close();
        connection.close();
    }
}
