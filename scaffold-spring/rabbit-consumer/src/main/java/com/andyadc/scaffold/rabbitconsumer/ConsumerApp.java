package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author andaicheng
 * @since 2017/11/15
 */
@EnableRabbit
@ComponentScan
@PropertySource("classpath:rabbit.properties")
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerApp.class);

        // only to keep running
        context.getBean("rabbitAdmin");

        //TimeUnit.SECONDS.sleep(100);

        //context.close();
    }
}
