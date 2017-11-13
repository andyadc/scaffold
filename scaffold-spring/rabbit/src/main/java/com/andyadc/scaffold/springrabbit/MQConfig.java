package com.andyadc.scaffold.springrabbit;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author andy.an
 * @since 2017/11/13
 */
@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://messager:messager@ip:5672");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

}
