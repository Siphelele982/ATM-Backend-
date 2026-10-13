package com.AtmApi.ATM_BACKEND.Service;

import com.AtmApi.ATM_BACKEND.Entity.Transaction;
import com.AtmApi.ATM_BACKEND.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public List<Transaction> getTransactionsByAccount(String accNum) {
        return transactionRepository.findByAccount_AccNum(accNum);
    }
}