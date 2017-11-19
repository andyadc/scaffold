package com.andyadc.scaffold.springrabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

/**
 * @author andy.an
 * @since 2017/11/15
 */
@ComponentScan
public class RabbitAnnotationApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitApp.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        Order order = new Order("1212", "was", 5, new Date());
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        //MessageProperties properties = MessagePropertiesBuilder.newInstance().setHeader("__TypeId__", "order").build();

        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.convertAndSend("adc.exchage.direct", "info", order);

        context.close();
    }
}
