package org.example.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerUtil {
    private static final String KAFKA_BROKER_URL = "localhost:9092";

    public static KafkaConsumer<String, String> createConsumer(String topic) {
        // Create consumer properties
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER_URL);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create Kafka consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    public static ConsumerRecords<String, String> pollRecords(KafkaConsumer<String, String> consumer) {
        try {
            // Poll for records
            return consumer.poll(Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}