package com.andyadc.scaffold.middleware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author andy.an
 * @since 2017/8/15
 */
public class MessagePoller implements Runnable, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(MessagePoller.class);

    private static AtomicLong tid = new AtomicLong(0);
    private Properties props;
    private String[] topics;
    private long pollTimeout = 0;
    private volatile boolean running = true;

    public MessagePoller() {
        this.props = getDefaultConfig();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Thread pollerThread = new Thread(this);
        pollerThread.setName("MessagePoller-" + tid.getAndIncrement());
        pollerThread.start();
    }

    @Override
    public void run() {

        while (running) {
            KafkaConsumer<String, String> kafkaConsumer = null;
            LOG.info("Starting message poller ....");

            try {
                kafkaConsumer = new KafkaConsumer<>(props);
                kafkaConsumer.subscribe(Arrays.asList(topics));

                while (running) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(pollTimeout);

                    if (records.isEmpty())
                        continue;
                    LOG.info(records.count() + " records have been polled!");

                    // TODO

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (kafkaConsumer != null) {
                    LOG.info("Closing message poller ...");
                    kafkaConsumer.close();
                }
            }

        }

        LOG.info("Shutdowned the message poller thread!");
    }

    private static Properties getDefaultConfig() {
        Properties props = new Properties();
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public void destroy() throws Exception {
        this.running = false;
    }

    public void setTopics(String[] topics) {
        LOG.info("Set topic list to: {}", Arrays.toString(topics));
        this.topics = topics;
    }

    public void setPollTimeout(long pollTimeout) {
        this.pollTimeout = pollTimeout;
    }
}
