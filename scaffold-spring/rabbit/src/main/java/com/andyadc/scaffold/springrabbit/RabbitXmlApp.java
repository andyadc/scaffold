package com.andyadc.scaffold.springrabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author andy.an
 * @since 2017/11/15
 */
public class RabbitXmlApp {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:rabbit-context.xml");

        RabbitTemplate rabbitTemplate = (RabbitTemplate) context.getBean("rabbitTemplate");

//        rabbitTemplate.convertAndSend("adc.exchage.direct", "error", "error le");
//        MessageProperties properties = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).setMessageId("123").build();
//        Message message = MessageBuilder.withBody("Builder".getBytes()).andProperties(properties).build();
//        rabbitTemplate.send("xmlQueue", message);

    }
}
