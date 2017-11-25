package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

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

        TimeUnit.SECONDS.sleep(10);

        context.close();
    }
}