# Kafka Couchbase Integration Test Automation Framework

## Authors
- [Rajat Goyal](rajat.goyal@statusneo.com) (https://github.com/errajatgoyal) - Framework contributor

## Description

This Automation testing Framework has the capability to connect with a queue like Apache Kafka and a NoSQL database like Couchbase for validating messages flowing across a system built on these technologies. It can read/write the messages from/to a Kafka topic as well as can read/write from/to a Couchbase document.
It also comes with the ability to read an API response message and use that to validate across the system, proof of which is demonstrated using WireMock.

## Prerequisites

Before running this POC, ensure that you have the following components set up and configured on your local environment:

- **Kafka and Zookeeper**: Install Kafka and Zookeeper locally and ensure they are running.
- **Couchbase Server**: Install and configure Couchbase Server locally.
- **Java Development Kit (JDK)**: Ensure that JDK 8 or higher is installed on your machine.
- **Maven**: Install Maven for building and managing dependencies.

## Installation and Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/kafka-couchbase-integration-poc.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd kafka-couchbase-integration-poc
   ```

3. **Set up Kafka and Couchbase**:
    - Start Kafka and Zookeeper services.
    - Set up a Kafka topic named `testTopic` for this POC.
    - Ensure that Couchbase Server is running and create a bucket named `test_bucket`.

4. **Build the project using Maven**:
   ```bash
   mvn clean install
   ```

## Usage

Run the Kafka and Couchbase integration test:
```bash
mvn test
```

This will execute the integration test `KafkaCouchbaseIntegrationTest`, which sends a message to Kafka, consumes it, and stores it in Couchbase. It validates that the message is correctly received and stored in Couchbase.

## Configuration

- **Kafka Broker URL**: Modify the `KAFKA_BROKER_URL` variable in `KafkaConsumerUtil` and `KafkaProducerUtil` classes if your Kafka broker is running on a different URL.
- **Couchbase Connection String and Bucket Name**: Modify the `COUCHBASE_CONNECTION_STRING` and `COUCHBASE_BUCKET_NAME` variables in the `CouchbaseUtil` class if your Couchbase setup differs.

## Contributing

Contributions are welcome! If you would like to contribute to this project, please follow these steps:

1. **Fork the repository**.
2. **Create a new branch** for your feature or bug fix: `git checkout -b feature/my-feature` or `git checkout -b bugfix/issue-number`.
3. **Make your changes and commit them**: `git commit -am 'Add new feature'`.
4. **Push to the branch**: `git push origin feature/my-feature`.
5. **Submit a pull request**.

Please review the [Contribution Guidelines](CONTRIBUTING.md) for more details.

## License

This project is licensed under the [MIT License](LICENSE).
