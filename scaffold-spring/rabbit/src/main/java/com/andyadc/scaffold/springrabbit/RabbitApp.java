package com.andyadc.scaffold.springrabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author andy.an
 * @since 2017/11/14
 */
@ComponentScan
public class RabbitApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitApp.class);

        //RabbitAdmin rabbitAdmin = (RabbitAdmin) context.getBean("rabbitAdmin");
        //rabbitAdmin.getQueueProperties("adc");

        //rabbitAdmin.declareExchange(new TopicExchange("adc.exchage.topic", true, false));
        //rabbitAdmin.declareExchange(new DirectExchange("adc.exchage.direct", true, false));
        //rabbitAdmin.declareExchange(new FanoutExchange("adc.exchage.fanout", true, false));
        //rabbitAdmin.declareExchange(new HeadersExchange("adc.exchage.header", true, false));

        //rabbitAdmin.deleteExchange("adc.exchage.direct");

        //rabbitAdmin.declareQueue(new Queue("log.debug", true));
        //rabbitAdmin.declareQueue(new Queue("log.info", true));
        //.declareQueue(new Queue("log.error", true));

        //rabbitAdmin.purgeQueue("log.debug", false);

//        Binding debugBinding = new Binding("log.debug", Binding.DestinationType.QUEUE, "adc.exchage.direct", "debug", null);
//        Binding infoBinding = new Binding("log.info", Binding.DestinationType.QUEUE, "adc.exchage.direct", "info", null);
//        Binding errorBinding = new Binding("log.error", Binding.DestinationType.QUEUE, "adc.exchage.direct", "error", null);
//        rabbitAdmin.declareBinding(debugBinding);
//        rabbitAdmin.declareBinding(infoBinding);
//        rabbitAdmin.declareBinding(errorBinding);
//
//        Binding builderBinding = BindingBuilder.bind(new Queue("log.error")).to(new TopicExchange("adc.exchage.topic")).with("error");


        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("desc", "from home");
//        Message message = new Message("hello home".getBytes(), messageProperties);
//
//        rabbitTemplate.setExchange("adc.exchage.direct");
//        rabbitTemplate.setRoutingKey("info");
//        rabbitTemplate.send(message);

        rabbitTemplate.convertAndSend("adc.exchage.direct", "debug", "this is a message");

        context.close();

    }
}
