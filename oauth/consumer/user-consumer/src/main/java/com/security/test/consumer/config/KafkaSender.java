package com.security.test.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 生产者
 *
 * @author Minko
 */
//@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息到kafka
     *
     * @param topic   主题
     * @param message 内容体
     */
    public void sendMsg(String topic, String message) {
        kafkaTemplate.send(topic, "key", message);
    }
}