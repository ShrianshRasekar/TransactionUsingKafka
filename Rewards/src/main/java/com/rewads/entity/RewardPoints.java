package com.rewads.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_points")
public class RewardPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int points;
    
    // Constructors
    public RewardPoints() {}

    public RewardPoints(Long userId, int points) {
        this.userId = userId;
        this.points = points;
    }

   

	// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}