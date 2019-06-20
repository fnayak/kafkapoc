package api.greet.util;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.JsonNode;

@Configuration
public class ProducerConfigUtility {
	
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";

	@Bean
	public KafkaProducer<String, JsonNode> creatProducer() {
		Properties properties = createConfigProp();
		KafkaProducer<String, JsonNode> producerFactory = new KafkaProducer(properties);
		return producerFactory;
	}
	
	public Properties createConfigProp() {
		Properties producerProp = new Properties();
		producerProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		producerProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return producerProp;
	}
	
	
	public void sendMessage(JsonNode payload, String topicName) {
		ProducerRecord<String, JsonNode> record = new ProducerRecord<>(topicName,payload);
		KafkaProducer<String, JsonNode> factory = creatProducer();
		factory.send(record);
	}
	
	
}
