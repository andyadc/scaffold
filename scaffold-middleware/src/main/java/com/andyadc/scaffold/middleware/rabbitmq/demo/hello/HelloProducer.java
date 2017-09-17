package com.andyadc.scaffold.middleware.rabbitmq.demo.hello;

import com.andyadc.scaffold.middleware.rabbitmq.demo.RabbitConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author andy.an
 * @since 2017/8/17
 */
public class HelloProducer {

    private static final String QUEUE_NAME = "rabbit.test";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername("messager");
        factory.setPassword("messager");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello rabbit";
        //发送消息到队列中
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
