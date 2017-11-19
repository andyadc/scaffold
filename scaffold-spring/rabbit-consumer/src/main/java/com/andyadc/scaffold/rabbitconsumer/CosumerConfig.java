package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;

/**
 * @author andaicheng
 * @since 2017/11/18
 */
@Configuration
public class CosumerConfig {

    //@Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer() {
        return new RabbitListenerConfigurer() {
            @Override
            public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {

                SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
                endpoint.setId("1");
                endpoint.setQueueNames("xmlQueue", "log.debug", "log.info", "log.error");
                endpoint.setMessageListener(message -> {
                    System.out.println("++++++++++++");
                    System.out.println(new String(message.getBody()));
                });

                registrar.registerEndpoint(endpoint);
            }
        };
    }
}
