package com.andyadc.scaffold.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author andaicheng
 * @since 2017/11/25
 */
public class ConsumerAck {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getHeaders().get("error") != null) {
                    // 支持批量
                    //this.getChannel().basicNack(envelope.getDeliveryTag(), false, false);
                    // 单个
                    this.getChannel().basicReject(envelope.getDeliveryTag(), false);

                    System.out.println("Cannot consumer message");
                    return;
//                    throw new IllegalArgumentException("Has error header");
                }
                System.out.println("Receive data: " + new String(body));

                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
                System.out.println("Consume complete");
            }
        };

        //channel.basicQos(1);
        channel.basicConsume("qq", false, consumer);

        TimeUnit.SECONDS.sleep(100);

        channel.close();
        connection.close();
    }
}
