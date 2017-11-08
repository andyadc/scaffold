package com.andyadc.scaffold.middleware.rabbitmq.demo.routing;

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
public class DirectConsumer {

    private static final String EXCHANGE_NAME = "direct_test";

    // 路由关键字
    //private static final String[] routingKeys = new String[]{"info", "warning"};
    private static final String[] routingKeys = new String[]{"error"};

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //产生一个随机的队列名称
        final String queue = channel.queueDeclare().getQueue();

        for (String key : routingKeys) {
            //根据路由关键字对队列进行绑定
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
