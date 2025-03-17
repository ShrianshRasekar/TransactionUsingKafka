package com.rewads.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.transaction.entity.Transaction;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

	// ✅ Consumer Factory for Integer (update transaction)
	@Bean
	public ConsumerFactory<String, Integer> integerConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, RewardsConstants.GROUP_ID);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		config.put("group.id", "group-1");
		;
		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Integer> integerKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Integer> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(integerConsumerFactory());
		return factory;
	}

	// ✅ Consumer Factory for JSON (add transaction)
	@Bean
	public ConsumerFactory<String, Transaction> transactionConsumerFactory() {
		JsonDeserializer<Transaction> jsonDeserializer = new JsonDeserializer<>(Transaction.class);
		jsonDeserializer.addTrustedPackages("*"); // Allow all packages
		jsonDeserializer.setRemoveTypeHeaders(false);
		jsonDeserializer.setUseTypeMapperForKey(false); // Ensure key mapping is correct

		return new DefaultKafkaConsumerFactory<>(Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ConsumerConfig.GROUP_ID_CONFIG, RewardsConstants.GROUP_ID2,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class), new StringDeserializer(),
				jsonDeserializer);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Transaction> transactionKafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = 
	        new ConcurrentKafkaListenerContainerFactory<>();
	    
	    factory.setConsumerFactory(transactionConsumerFactory());
	    factory.setBatchListener(false); // Ensures individual messages are processed

	    return factory;
	}


}
