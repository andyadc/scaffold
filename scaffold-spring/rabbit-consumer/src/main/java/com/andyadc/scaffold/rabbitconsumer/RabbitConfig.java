package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author andaicheng
 * @since 2017/11/15
 */
@Configuration
public class RabbitConfig {

    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://messager:messager@www.jd-server.com:5672");
        return factory;
    }

    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory());
        return listenerContainerFactory;
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

        // ack
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        //container.setMessageListener(new QueueMessageListener());

        // ack
        container.setMessageListener(new QueueAwareMessageListener());

        //----------MessageListenerAdapter----

//        MessageListenerAdapter adapter = new MessageListenerAdapter();
//        adapter.setDelegate(new MessageHandler());
//
//        Map<String, String> queueOrTagToMethodName = new HashMap<>();
//        queueOrTagToMethodName.put("log.debug", "handleDebug");
//        queueOrTagToMethodName.put("log.info", "handleInfo");
//        queueOrTagToMethodName.put("log.error", "handleError");
//        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
//        adapter.setDefaultListenerMethod("handleMessage");
//
//        Map<String, Class<?>> idClassMapping = new HashMap<>();
//        idClassMapping.put("order", Order.class);
//
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper mapper = new DefaultJackson2JavaTypeMapper();
//        mapper.setIdClassMapping(idClassMapping);
//
//        converter.setJavaTypeMapper(mapper);
//
//        adapter.setMessageConverter(converter);
//
//        container.setMessageListener(adapter);

        return container;
    }
}
