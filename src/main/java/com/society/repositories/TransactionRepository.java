package com.society.repositories;

import com.society.entities.account.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findByTransId(Integer transactionId);

    List<Transaction> findAllByOrderByTransDateDesc();
}
