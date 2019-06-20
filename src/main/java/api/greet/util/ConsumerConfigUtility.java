package api.greet.util;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.JsonNode;

@Configuration
public class ConsumerConfigUtility {
	
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";

	@Bean
	public KafkaConsumer<String, JsonNode> creatConsumer() {
		Properties properties = createConfigProp();
		KafkaConsumer<String, JsonNode> consumer = new KafkaConsumer(properties);
		return consumer;
	}
	
	public Properties createConfigProp() {
		Properties consumerProp = new Properties();
		consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		consumerProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		consumerProp.put(ConsumerConfig.GROUP_ID_CONFIG, "demoGroup");
		return consumerProp;
	}
	
	
	public ConsumerRecords<String, JsonNode> subscribeMessage(String topicName) {
		KafkaConsumer<String, JsonNode> consumer = creatConsumer();
		consumer.subscribe(Arrays.asList(topicName));
		ConsumerRecords<String, JsonNode> records = consumer.poll(3000);
		return records;
	}
	
	
}
