package org.example;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import org.example.util.CouchbaseUtil;
import org.example.util.KafkaConsumerUtil;
import org.example.util.KafkaProducerUtil;
import org.example.util.WireMockConfig;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.LocalDateTime;
import java.util.Iterator;

public class KafkaCouchbaseIntegrationTest {

    private static final String KAFKA_TOPIC = "testTopic";
    private static final String COUCHBASE_BUCKET_NAME = "test_bucket";

    @BeforeClass
    public static void setUp() {
        WireMockConfig.start();
    }

    @AfterClass
    public static void tearDown() {
        WireMockConfig.stop();
    }

    @Test
    public void testDataInCouchbaseAfterKafka() {

        // Send event to Kafka topic (Mocked using WireMock)
        String message = WireMockConfig.fetchDataFromApi()+" - "+LocalDateTime.now();

        // Send event to Kafka topic
        KafkaProducerUtil.sendMessage(KAFKA_TOPIC, message);

        // Create Couchbase client
        Cluster cluster = CouchbaseUtil.connectToCluster("administrator", "administrator");
        Collection collection = CouchbaseUtil.getDefaultCollection(cluster);

        // Create Kafka consumer
        try (KafkaConsumer<String, String> consumer = KafkaConsumerUtil.createConsumer(KAFKA_TOPIC)) {
            // Consume event from Kafka topic
            ConsumerRecords<String, String> consumerRecords = KafkaConsumerUtil.pollRecords(consumer);

            if (consumerRecords.isEmpty()) {
                // Kafka server is unreachable or no messages were received
                Assert.fail("No records received from Kafka. Kafka server may be down or unreachable.");
            }

            Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
            while (iterator.hasNext()) {
                ConsumerRecord<String, String> consumerRecord = iterator.next();
                // Getting data from Kafka
                String receivedMessage = consumerRecord.value();

                // Inserting the Kafka retrieved value to Couchbase
                CouchbaseUtil.upsertDocument(collection, "doc_1", receivedMessage);

                // Retrieve and validate data from Couchbase
                JsonObject getResult = CouchbaseUtil.getDocument(collection, "doc_1");

                // Print the values from Couchbase and Kafka in the console
                System.out.println("This is the message from Couchbase: - \n" + getResult.toString());
                System.out.println("This is the message in the Kafka queue: - \n" + message);

                // Validate that both are the same message
                Assert.assertEquals(receivedMessage, message);
            }
        } catch (Exception e) {
            // Exception occurred, Kafka server may be down or unreachable
            Assert.fail("Exception occurred while consuming records from Kafka: " + e.getMessage());
        } finally {
            // Disconnect from Couchbase
            cluster.disconnect();
        }
    }
}