package api.greet.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import api.greet.model.Vendor;
import api.greet.util.ConsumerConfigUtility;
import api.greet.util.ProducerConfigUtility;

@RestController
public class MessageController {
	private static final String TOPIC_NAME = "VendorTopic";
	
	@Autowired
	ProducerConfigUtility producerConfigUtility;
	
	@Autowired
	ConsumerConfigUtility consumerConfigUtility;
	
	
	
	@PostMapping("/message/publish")
	public void sendMessage() {
		JsonNode payload = getPayload();
		producerConfigUtility.sendMessage(payload,TOPIC_NAME);
		System.out.println("Message Sent");
	}
	
	@GetMapping("/message/subscribe")
	public ResponseEntity<List<Vendor>> subscribeMessage() {
		ConsumerRecords<String, JsonNode> records = consumerConfigUtility.subscribeMessage(TOPIC_NAME);
		List<Vendor> vendorList = displayMessage(records);
		return new ResponseEntity<List<Vendor>>(vendorList, HttpStatus.OK);
	}
	
	public List<Vendor> displayMessage(ConsumerRecords<String, JsonNode> records) {
		ObjectMapper objMapper = new ObjectMapper();
		List<Vendor> vendorList = null;
		for (ConsumerRecord<String, JsonNode> record : records) {
		
			JsonNode jsonNode = (JsonNode)record.value();
			System.out.println("JsonNode" + jsonNode);
			try {
				ObjectReader reader = objMapper.readerFor(new TypeReference<List<Vendor>>() {});
				vendorList = reader.readValue(jsonNode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vendorList;
	}
	
	public JsonNode getPayload() {
	  ObjectMapper mapper = new ObjectMapper(); 
	  List<Vendor> vendorList = new	ArrayList<>(); 
	  vendorList.add(new Vendor(101,"IBM")); 
	  vendorList.add(new Vendor(103,"HCL")); 
	  vendorList.add(new Vendor(105,"UHG")); 
	  vendorList.add(new Vendor(109,"MICROSOFT"));
	  JsonNode jsonNode = mapper.valueToTree(vendorList);
	  return jsonNode;
	}
}
