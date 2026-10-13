package com.AtmApi.ATM_BACKEND.Repository;

import com.AtmApi.ATM_BACKEND.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount_AccNum(String accNum);
}