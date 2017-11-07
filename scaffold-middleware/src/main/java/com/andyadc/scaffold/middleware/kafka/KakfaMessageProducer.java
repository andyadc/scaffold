package com.andyadc.scaffold.middleware.kafka;

import com.alibaba.fastjson.JSON;
import com.andyadc.scaffold.middleware.message.Message;
import com.andyadc.scaffold.middleware.message.MessageProducer;
import com.andyadc.scaffold.util.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author andy.an
 * @since 2017/8/15
 */
public class KakfaMessageProducer implements MessageProducer, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(KakfaMessageProducer.class);

    private Properties props;
    private String bootstrapServers;
    private String defaultAppId;

    private Producer<String, String> kafkaProducer;

    public KakfaMessageProducer() {
        this.props = getDefaultConfig();
    }

    private static Properties getDefaultConfig() {
        Properties props = new Properties();
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        return props;
    }

    @Override
    public void send(String topic, Message<?> message) {
        this.send(topic, null, message);
    }

    @Override
    public void send(String topic, String key, Message<?> message) {
        sendToKafka(topic, key, message);
    }

    protected Future<RecordMetadata> sendToKafka(String topic, String key, Message<?> message) {
        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }
        if (StringUtils.isBlank(message.getAppId())) {
            message.setAppId(defaultAppId);
        }
        if (message.getSendTime() == null) {
            message.setSendTime(new Date());
        }
        String json = JSON.toJSONStringWithDateFormat(message, "yyyy-MM-dd HH:mm:ss.SSS");
        LOG.debug("sending topic {} with key {} and message: {}", topic, key, message);
        return kafkaProducer.send(new ProducerRecord<>(topic, key, json));
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public void setDefaultAppId(String defaultAppId) {
        this.defaultAppId = defaultAppId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("kafka producer is initializing");
        if (StringUtils.isNotBlank(bootstrapServers)) {
            props.put("bootstrap.servers", bootstrapServers);
        }
        this.kafkaProducer = new KafkaProducer<>(props);
        LOG.info("kafka producer is initialized");
    }

    @Override
    public void destroy() throws Exception {
        if (this.kafkaProducer != null) {
            LOG.info("kafka producer is closing...");
            kafkaProducer.flush();
            kafkaProducer.close();
        }
        LOG.info("kafka producer is closed!");
    }
}
