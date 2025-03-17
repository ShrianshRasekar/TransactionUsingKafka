package com.rewads.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.rewads.config.RewardsConstants;
import com.rewads.entity.RewardPoints;
import com.rewads.repo.RewardPointsRepository;
import com.transaction.entity.Transaction;

@Service
public class RewardsService {

    private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);
    private final RewardPointsRepository rewardPointsRepository;

    public RewardsService(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    @KafkaListener(topics = RewardsConstants.TRANSACTION_UPDATE_TOPIC, groupId = RewardsConstants.GROUP_ID)
    public void consumeUpdatedTransaction(Integer amt) {
        logger.info("Consumed Transaction Update: Amount = {}", amt);
        // Logic for reward calculation if needed
    }

    @KafkaListener(topics = RewardsConstants.TRANSACTION_ADD_TOPIC, groupId = RewardsConstants.GROUP_ID2, containerFactory = "transactionKafkaListenerContainerFactory")
    public void consumeAddedTransaction(Transaction ts) {
        logger.info("Consumed Transaction: {}", ts);

        if (ts == null || ts.getUserId() == null || ts.getAmount() == null || ts.getTransactionType() == null) {
            logger.warn("Invalid transaction data received");
            return;
        }

        int earnedPoints = calculateRewardPoints(ts.getTransactionType(), ts.getAmount());
        updateRewardPoints(ts.getUserId(), earnedPoints);
    }

    public void updateRewardPoints(Long userId, double amount, String transactionType) {
        int earnedPoints = calculateRewardPoints(transactionType, amount);
        updateRewardPoints(userId, earnedPoints);
    }

    private int calculateRewardPoints(String transactionType, double amount) {
        int points = 0;
        if ("Retail".equalsIgnoreCase(transactionType)) {
            points = (int) (amount / 100) * 2;
        } else if ("Movies".equalsIgnoreCase(transactionType) ||
                   "Groceries".equalsIgnoreCase(transactionType) ||
                   "Dining".equalsIgnoreCase(transactionType) ||
                   "Departmental Store".equalsIgnoreCase(transactionType)) {
            points = (int) (amount / 100) * 10;
        } else if ("Bill Payment".equalsIgnoreCase(transactionType)) {
            points = (int) (amount / 100) * 4;
        }
        return points;
    }

    private void updateRewardPoints(Long userId, int earnedPoints) {
        Optional<RewardPoints> existingReward = rewardPointsRepository.findByUserId(userId);

        if (existingReward.isPresent()) {
            RewardPoints rewardPoints = existingReward.get();
            rewardPoints.setPoints(rewardPoints.getPoints() + earnedPoints);
            rewardPointsRepository.save(rewardPoints);
        } else {
            RewardPoints newReward = new RewardPoints(userId, earnedPoints);
            rewardPointsRepository.save(newReward);
        }

        logger.info("Updated reward points for user {}: {} points", userId, earnedPoints);
    }

    public Optional<RewardPoints> getRewardPointsByUserId(Long userId) {
        return rewardPointsRepository.findByUserId(userId);
    }
}
