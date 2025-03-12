package com.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.service.KafkaService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	private KafkaService kafkaservice;
	
	@PostMapping("/addAmount/{amt}")
	public Integer updateTransaction(@PathVariable("amt") Integer amt) {
		this.kafkaservice.updateTransaction(amt);
		return amt;
	}

}
