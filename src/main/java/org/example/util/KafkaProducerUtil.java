package org.example.util;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerUtil {
    private static final String KAFKA_BROKER_URL = "localhost:9092";

    public static void sendMessage(String topic, String message) {
        // Create producer properties
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER_URL);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Kafka producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

        try {
            // Send event to Kafka topic
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            producer.send(record);
            producer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close Kafka producer
            producer.close();
        }
    }
}
