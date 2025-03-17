package com.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.transaction.config.TransactionConstants;
import com.transaction.entity.Transaction;

@Service
public class KafkaService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaService	.class);
	private final KafkaTemplate<String, Integer> integerKafkaTemplate;
	private final KafkaTemplate<String, Transaction> jsonKafkaTemplate;

	public KafkaService(KafkaTemplate<String, Integer> integerKafkaTemplate, 
	                               KafkaTemplate<String, Transaction> jsonKafkaTemplate) {
	        this.integerKafkaTemplate = integerKafkaTemplate;
	        this.jsonKafkaTemplate = jsonKafkaTemplate;
	    }

	public boolean updateTransaction(int amt) {
	    integerKafkaTemplate.send(TransactionConstants.TRANSACTION_TOPIC_NAME, amt)
	        .whenComplete((result, ex) -> {
	            if (ex == null) {
	                logger.info("Transaction update sent successfully: " + amt);
	            } else {
	                logger.error("Failed to send transaction update", ex);
	            }
	        });
	    return true;
	}


	public boolean addTransaction(Transaction ts) {
		this.jsonKafkaTemplate.send(TransactionConstants.TRANSACTION_ADD_TOPIC, ts);
		logger.info("Transaction added for user " + ts);
		return true;
	}
}
