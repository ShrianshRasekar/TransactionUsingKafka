package com.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.entity.Transaction;
import com.transaction.service.KafkaService;
import com.transaction.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	private KafkaService kafkaservice;
	
	@Autowired
	private TransactionService tservice;
	
		
	@PostMapping("/addAmount/{amt}")
	public Integer updateTransaction(@PathVariable("amt") Integer amt) {
		this.kafkaservice.updateTransaction(amt);
		return amt;
	}
	
	@GetMapping("/{tid}")
	public Transaction getTransaction(@PathVariable("tid") Long tid) {
		
		return tservice.getTransactionById(tid);
	}
	
	@PostMapping(path="/addTransaction",produces=MediaType.APPLICATION_JSON_VALUE)
	public Transaction addNewTransaction(@RequestBody Transaction transaction) {
		
		Transaction ts= tservice.addNewTransaction(transaction);
		
		this.kafkaservice.addTransaction(ts);
		System.out.println(ts);
		return ts;
	}
	
	
	
	

}
