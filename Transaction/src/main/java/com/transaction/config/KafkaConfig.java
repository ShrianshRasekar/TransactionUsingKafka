package com.transaction.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
	
	@Bean
	public NewTopic userTransaction() {
		
		return TopicBuilder.name(TransactionConstants.TRANSACTION_TOPIC_NAME)
				//.replicas(0)
				//.partitions(0)
				.build();
		
	}

}
