# Debezium Demo with Kafka

This project demonstrates how to integrate Kafka with Debezium for real-time data streaming and change data capture (CDC). It showcases a simple setup where changes in a PostgreSQL database are captured and sent to Kafka topics using Debezium.

## Technologies
The following technologies are used in this demo:
- **Kafka**: Distributed event streaming platform
- **Debezium**: Open-source CDC tool for capturing database changes
- **Kowl**: A web UI for Kafka
- **PostgreSQL**: Relational database used for data changes
- **Java & Spring Boot**: Backend application
- **Docker & Docker Compose**: Containerization and orchestration tools

## Requirements
- Docker and Docker Compose installed on your machine
- Java 17 or later
- Maven

## Setup

### 1. Clone the repository
```bash
git clone https://github.com/ErtanSidar/debezium-demo.git
```
Locate int the project


### 2\. Create Docker containers

Run the following command to start the necessary services (Kafka, PostgreSQL, Debezium, Kowl) using Docker Compose:
```bash
docker-compose up -d
```

This will create the following services:

*   Kafka broker (kafka:9092)
    
*   Debezium connector
    
*   PostgreSQL database (db:5555)
    
*   Kowl web interface (localhost:8090)
    

### 3\. PostgreSQL Configuration

The PostgreSQL database comes pre-configured with a sample schema and table for tracking changes. The database is set up with the following credentials:

*   **Username**: postgres
    
*   **Password**: postgres
    
*   **Database Name**: essoftdb
    

Running the Application
-----------------------

Once the Docker containers are up and running, the next step is to set up the Debezium connector to capture changes from the PostgreSQL database and stream them into Kafka.

### 4\. Set up Debezium PostgreSQL Connector

You can configure the Debezium connector by sending a POST request to the Kafka Connect API. Use the following configuration in the body of the request:

```json
{
  "name": "essoft",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "tasks.max": "1",
    "database.history.kafka.bootstrap.servers": "kafka:9092",
    "database.history.kafka.topic": "schema-changes.movies",
    "database.hostname": "db",
    "database.port": "5432",
    "database.user": "postgres",
    "database.password": "postgres",
    "database.dbname": "essoftdb",
    "database.server.name": "db",
    "tombstones.on.delete": "false",
    "topic.prefix": "product",
    "table.include.list": "public.product",
    "heartbeat.interval.ms": "5000",
    "key.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter.schemas.enable": "false",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
    "plugin.name": "pgoutput"
  }
}

```


Send this request to:

POST http://localhost:8083/connectors

This will create a connector named essoft that listens for changes in the public.product table and streams them into Kafka topics with the prefix product.

Testing the Integration
-----------------------

1.  After setting up the connector, any changes in the public.product table of the PostgreSQL database will be captured by Debezium and sent to Kafka.
    
2.  You can use the Kowl interface (localhost:8090) to view the messages in the Kafka topics and monitor the data flow.
