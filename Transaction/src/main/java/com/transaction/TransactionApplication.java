package com.transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.transaction.entity.Transaction;

@SpringBootApplication
public class TransactionApplication {

 

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
		System.out.println("Transaction Service Running");
		
		Transaction t1=new Transaction(1,1500,"retail");
		
		Transaction t2=new Transaction(1, 1500, "retail");
		//System.out.println(t1.toString());
		System.out.println(t1.equals(t2));
		
		Map<Transaction,Integer> m1=new HashMap<>();
		m1.put( t1,1);
		m1.put(t2,1);
		
		for(Map.Entry<Transaction,Integer > l:m1.entrySet()){
			System.out.println(l.getKey()+" -> "+l.getValue());
		}
	}

}
