package com.andyadc.scaffold.rabbitconsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author andaicheng
 * @since 2017/11/18
 */
@Component
public class SimpleMessageHandler {

    @RabbitListener(queues = {"log.debug"})
    public void debugHandle(@Payload String body, @Headers Map<String, String> headers) {
        System.out.println("//////////// " + headers);
        System.out.println("//////////// " + body);
    }

    @RabbitListener(queues = "${queue.name.error}")
    public void errorHandle(byte[] bytes) {
        System.out.println("\\\\" + new String(bytes));
    }
}
