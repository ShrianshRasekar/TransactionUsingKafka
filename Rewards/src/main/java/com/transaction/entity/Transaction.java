package com.transaction.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Transaction implements Serializable {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("userId")
	private Long userId;

	@JsonProperty("amount")
	private Double amount;

	@JsonProperty("transactionType")
	private String transactionType;

	@JsonProperty("transactionDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime transactionDate;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(Long id, Long userId, Double amount, String transactionType, LocalDateTime transactionDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userId=" + userId + ", amount=" + amount + ", transactionType="
				+ transactionType + ", transactionDate=" + transactionDate + "]";
	}

}
