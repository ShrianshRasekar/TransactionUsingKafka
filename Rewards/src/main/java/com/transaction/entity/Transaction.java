package com.transaction.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userId=" + userId + ", amount=" + amount + ", transactionType="
				+ transactionType + ", transactionDate=" + transactionDate + "]";
	}

}
