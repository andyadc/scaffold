package com.andyadc.scaffold.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnCallback;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author andaicheng
 * @since 2017/11/25
 */
public class ReturnListenerTest {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("-------------handleReturnListener-----------");
                System.out.println("replyCode > " + replyCode);
                System.out.println("replyText > " + replyText);
                System.out.println("exchange > " + exchange);
                System.out.println("routingKey > " + routingKey);
                System.out.println("body > " + new String(body));
            }
        });

        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(com.rabbitmq.client.Return returnMessage) {
                System.out.println("-------------handleReturnCallback-----------");
                System.out.println(returnMessage);
            }
        });


        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().contentEncoding("UTF-8").build();
        channel.basicPublish("adc.exchage.direct", "fatal", true, properties, "No home".getBytes());

        TimeUnit.SECONDS.sleep(10);

        channel.close();
        connection.close();
    }
}
