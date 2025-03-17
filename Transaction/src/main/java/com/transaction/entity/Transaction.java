package com.transaction.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Fix Hibernate proxy issue
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    private Long id;  // Changed 'tid' to 'id' for consistency

    private Long userId; 

    @Column(nullable = false)
    private Double amount; 

    @Column(nullable = false)
    private String transactionType; 

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;


    public Transaction() {}

    public Transaction(Long userId, Double amount, String transactionType, LocalDateTime transactionDate) {
        this.userId = userId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    // Auto-set transactionDate if not provided
    @PrePersist
    protected void onCreate() {
        if (transactionDate == null) {
            this.transactionDate = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public String toString() {
        return "Transaction{" +
               "id=" + id +
               ", userId=" + userId +
               ", amount=" + amount +
               ", transactionType='" + transactionType + '\'' +
               ", transactionDate=" + transactionDate +
               '}';
    }
}
