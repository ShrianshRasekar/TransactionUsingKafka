package com.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.transaction.entity.Transaction;

@EnableJpaRepositories
public interface TransactionRepo extends JpaRepository<Transaction, Long>{

}
