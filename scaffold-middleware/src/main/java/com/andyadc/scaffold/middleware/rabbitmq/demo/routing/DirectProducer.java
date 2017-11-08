package com.andyadc.scaffold.middleware.rabbitmq.demo.routing;

import com.andyadc.scaffold.middleware.rabbitmq.demo.RabbitConst;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author andy.an
 * @since 2017/8/18
 */
public class DirectProducer {

    private static final String EXCHANGE_NAME = "direct_test";

    // 路由关键字
    private static final String[] routingKeys = new String[]{"info", "warning", "error"};

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        for (String key : routingKeys) {
            String message = "DirectProducer message level: " + key;
            channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
        }

        channel.close();
        connection.close();
    }
}
