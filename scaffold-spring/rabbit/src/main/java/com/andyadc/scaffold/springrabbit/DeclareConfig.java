package com.andyadc.scaffold.springrabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andaicheng
 * @since 2017/11/14
 */
@Configuration
public class DeclareConfig {

    @Bean
    public List<Binding> directBindings() {
        List<Binding> bindings = new ArrayList<>();
        bindings.add(BindingBuilder.bind(new Queue("log.error")).to(new DirectExchange("adc.exchage.direct")).with("error"));
        bindings.add(BindingBuilder.bind(new Queue("log.info")).to(new DirectExchange("adc.exchage.direct")).with("info"));
        bindings.add(BindingBuilder.bind(new Queue("log.debug")).to(new DirectExchange("adc.exchage.direct")).with("debug"));
        return bindings;
    }

    @Bean
    public List<Queue> logQueues() {
        List<Queue> queueList = new ArrayList<>();
        queueList.add(new Queue("log.debug", true));
        queueList.add(new Queue("log.info", true));
        queueList.add(new Queue("log.error", true));
        return queueList;
    }

    @Bean
    public Exchange directExchange() {
        return new DirectExchange("adc.exchage.direct", true, false);
    }

    @Bean
    public Exchange topicExchange() {
        return new TopicExchange("adc.exchage.topic", true, false);
    }

    @Bean
    public Exchange fanoutExchange() {
        return new FanoutExchange("adc.exchage.fanout", true, false);
    }

    @Bean
    public Exchange headersExchange() {
        return new HeadersExchange("adc.exchage.headers", true, false);
    }
}
