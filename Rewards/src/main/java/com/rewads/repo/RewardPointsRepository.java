package com.rewads.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewads.entity.RewardPoints;

import java.util.Optional;

@Repository
public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long> {
    Optional<RewardPoints> findByUserId(Long userId);
}