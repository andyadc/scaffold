package com.andyadc.scaffold.middleware.rabbitmq.demo.topic;

import com.andyadc.scaffold.middleware.rabbitmq.demo.RabbitConst;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author andy.an
 * @since 2017/8/18
 */
public class TopicCosumer {

    private static final String EXCHANGE_NAME = "topic_test";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queue = channel.queueDeclare().getQueue();

        // 路由关键字
        // * ：可以替代一个词, #：可以替代0或者更多的词
        String[] routingKeys = new String[]{"*.orange.*"};
        //String[] routingKeys = new String[]{"*.*.rabbit", "lazy.#"};

        //绑定路由
        for (String key : routingKeys) {
            channel.queueBind(queue, EXCHANGE_NAME, key);
        }

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Consumer routingKey " + envelope.getRoutingKey() + " Received '" + message + "'");
            }
        };

        channel.basicConsume(queue, true, consumer);
    }
}
