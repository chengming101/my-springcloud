package com.security.test.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

//@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = "test34563", groupId = "group1")
    public void onMessage(ConsumerRecord<String, String> message) {
        log.info("接收到消息====={}", message);
    }

    //    @KafkaListener(topics = "test",groupId = "group2")
    public void onMessage1(ConsumerRecord<String, String> message) {
        log.info("接收到消息111====={}", message);
    }
}
