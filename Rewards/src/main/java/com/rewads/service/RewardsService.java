package com.rewads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rewads.config.RewardsConstants;
import com.transaction.entity.Transaction;

@Service
public class RewardsService {
    
    private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);

    @KafkaListener(topics = RewardsConstants.TRANSACTION_UPDATE_TOPIC, groupId = RewardsConstants.GROUP_ID)
    public void consumeUpdatedTransaction(Integer amt) {
        logger.info("Consumed Transaction Update: Amount = {}", amt);
        // Logic for reward calculation
    }

    @KafkaListener(topics = RewardsConstants.TRANSACTION_ADD_TOPIC, groupId = RewardsConstants.GROUP_ID2, containerFactory = "transactionKafkaListenerContainerFactory")
    public void consumeAddedTransaction(Transaction ts) {
        logger.info("Consumed Transaction: {}", ts);
        // Logic for processing transaction details
    }
}
