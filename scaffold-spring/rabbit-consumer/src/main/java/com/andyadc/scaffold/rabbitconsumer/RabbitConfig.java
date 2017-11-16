package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andaicheng
 * @since 2017/11/15
 */
@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://messager:messager@www.jd-server.com:5672");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("xmlQueue", "log.debug", "log.info", "log.error");

        //container.setMessageListener(new QueueMessageListener());

        MessageListenerAdapter adapter = new MessageListenerAdapter();
        adapter.setDelegate(new MessageHandler());

        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("log.debug", "handleDebug");
        queueOrTagToMethodName.put("log.info", "handleInfo");
        queueOrTagToMethodName.put("log.error", "handleError");
        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        adapter.setDefaultListenerMethod("handleMessage");

        container.setMessageListener(adapter);

        return container;
    }
}
