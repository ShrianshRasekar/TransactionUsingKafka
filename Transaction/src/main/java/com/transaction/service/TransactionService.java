package com.transaction.service;

import org.springframework.stereotype.Service;

import com.transaction.entity.Transaction;


public interface TransactionService {
	
	public Transaction getTransactionById(Long tid);
	
	public Transaction addNewTransaction(Transaction ts);

}
