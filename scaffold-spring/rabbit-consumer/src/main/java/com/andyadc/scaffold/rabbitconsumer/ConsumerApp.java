package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

/**
 * @author andaicheng
 * @since 2017/11/15
 */
@ComponentScan
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerApp.class);

        TimeUnit.SECONDS.sleep(10);

        context.close();
    }
}
