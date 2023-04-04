package com.demo.kafka;

import java.util.UUID;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.log4j.Logger;

class SimpleProducer extends AbstractSimpleKafka {
    private KafkaProducer<String, String> kafkaProducer;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final Logger log = Logger.getLogger(SimpleProducer.class.getName());
    private String topicName = null;

    public SimpleProducer() throws Exception { }
    private void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    private String getTopicName() {
        return this.topicName;
    }

    public void run(String topicName, int numberOfMessages) throws Exception {
        int i = 0;
        while (i <= numberOfMessages) {
            String key = UUID.randomUUID().toString();
            String message = MessageHelper.getRandomString();
            this.send(topicName, key, message);
            i++;
            Thread.sleep(100);
        }
        this.shutdown();
    }

    public void runAlways(String topicName, KafkaMessageHandler callback) throws Exception {
        while (true) {
            String key = UUID.randomUUID().toString();
            String message = MessageHelper.getRandomString();
            this.send(topicName, key, message);
            Thread.sleep(100);
        }
    }
    protected void send(String topicName, String key, String message) throws Exception {
        String source = SimpleProducer.class.getName();
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(topicName, key, message);
        JSONObject obj = MessageHelper.getMessageLogEntryJSON(source, topicName, key, message);
        log.info(obj.toJSONString());
        getKafkaProducer().send(producerRecord);
    }

    private KafkaProducer<String, String> getKafkaProducer() throws Exception {
        if (this.kafkaProducer == null) {
            Properties props = PropertiesHelper.getProperties();
            this.kafkaProducer = new KafkaProducer<>(props);
        }
        return this.kafkaProducer;
    }

    public void shutdown() throws Exception {
        closed.set(true);
        log.info(MessageHelper.getSimpleJSONObject("Shutting down producer"));
        getKafkaProducer().close();
    }
}
