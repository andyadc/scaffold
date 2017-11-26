package com.andyadc.scaffold.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author andaicheng
 * @since 2017/11/26
 */
@Configuration
public class DeclareConfig {

    @Bean
    public Queue bootQueue() {
        return new Queue("boot", true, false, false);
    }

}
