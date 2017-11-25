package com.andyadc.scaffold.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author andaicheng
 * @since 2017/11/25
 */
public class PublishConfirmTest {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 确认模式
        channel.confirmSelect();
        // 事务模式 (与确认模式互斥)
        //channel.txSelect();

        channel.addConfirmListener(new ConfirmListener() {
            /**
             *
             * @param deliveryTag 消息id
             * @param multiple 是否批量确认
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleAck deliveryTag: " + deliveryTag + ", multiple: " + multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleNack deliveryTag: " + deliveryTag + ", multiple: " + multiple);
            }
        });

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().contentEncoding("UTF-8").build();
        channel.basicPublish("", "qq", properties, "Hello Rabbit".getBytes());
        channel.waitForConfirms();
        channel.basicPublish("", "qq", properties, "Hello Rabbit".getBytes());
        channel.waitForConfirms();


        TimeUnit.SECONDS.sleep(10);

        channel.close();
        connection.close();

    }
}
