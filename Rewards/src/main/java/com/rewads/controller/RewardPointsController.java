package com.rewads.controller;

import com.rewads.entity.RewardPoints;
import com.rewads.service.RewardsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rewards")
public class RewardPointsController {

	@Autowired
    private RewardsService rewardsService;

    public RewardPointsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRewards(
            @RequestParam Long userId, 
            @RequestParam Double amount, 
            @RequestParam String transactionType) {
        rewardsService.updateRewardPoints(userId, amount, transactionType);
        return ResponseEntity.ok("Reward points updated successfully.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getRewardPoints(@PathVariable("userId") Long userId) {  // Ensure parameter name matches the URL path
        Optional<RewardPoints> rewardPoints = rewardsService.getRewardPointsByUserId(userId);
        return rewardPoints.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
