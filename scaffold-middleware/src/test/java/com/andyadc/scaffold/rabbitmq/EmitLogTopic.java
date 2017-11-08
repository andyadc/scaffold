package com.andyadc.scaffold.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author andy.an
 * @since 2017/11/8
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String message = "a topic";
        String routingKey = "tom.man";

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        channel.close();
        connection.close();
    }
}
