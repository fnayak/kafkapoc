# kafkapoc
#kafka poc for publish and subscibe vendor list

Prerequisite
-------------------
Download kafka from below link. https://www.apache.org/dyn/closer.cgi?path=/kafka/2.0.0/kafka_2.11-2.0.0.tgz
Extract it.
kafka_2.11-2.0.0.tgz to kafka_2.11-2.0.0
Change log.dirs in the kafka_2.11-2.0.0\config\server.properties
log.dirs=C:\POC\kafka_2.11-2.0.0\kafka-logs
Set system environment variable.
C:\Program Files\Java\jre1.8.0_171\bin
Navigate to kafka directory.
Start Zookeeper in windows by run the command
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
Start Kafka server in window by run the command
bin\windows\kafka-server-start.bat config\server.properties
Create a topic
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic HubTopic
create ProducerConsumer program using SpringBoot
