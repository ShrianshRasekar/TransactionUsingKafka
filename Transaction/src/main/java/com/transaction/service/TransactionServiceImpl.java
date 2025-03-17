package com.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.entity.Transaction;
import com.transaction.repo.TransactionRepo;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionRepo tRepo;

	@SuppressWarnings("deprecation")
	public Transaction getTransactionById(Long tid) {
		
		return tRepo.getById(tid);
	}

	@Override
	public Transaction addNewTransaction(Transaction ts) {
		// TODO Auto-generated method stub
		return tRepo.save(ts);
	}

}
