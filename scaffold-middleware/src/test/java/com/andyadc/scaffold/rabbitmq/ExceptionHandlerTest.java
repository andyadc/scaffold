package com.andyadc.scaffold.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.impl.DefaultExceptionHandler;

/**
 * @author andaicheng
 * @since 2017/11/26
 */
public class ExceptionHandlerTest {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConst.SERVER_HOST);
        factory.setPort(RabbitConst.SERVER_PORT);
        factory.setUsername(RabbitConst.USERNAME);
        factory.setPassword(RabbitConst.PASSWORD);
        factory.setExceptionHandler(new MyExceptionHandler());

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.close();
        connection.close();
    }

    static class MyExceptionHandler extends DefaultExceptionHandler {
        @Override
        public void handleConsumerException(Channel channel, Throwable exception, Consumer consumer, String consumerTag, String methodName) {
            System.out.println("---------consumer exception---------");
            super.handleConsumerException(channel, exception, consumer, consumerTag, methodName);
        }

        // ...
    }
}
