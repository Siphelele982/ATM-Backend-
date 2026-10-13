package com.AtmApi.ATM_BACKEND.Service;

import com.AtmApi.ATM_BACKEND.DTO.TransactionResponse;
import com.AtmApi.ATM_BACKEND.Entity.Account;
import com.AtmApi.ATM_BACKEND.Repository.AccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import com.AtmApi.ATM_BACKEND.Entity.Transaction;
import com.AtmApi.ATM_BACKEND.Exception.AccountNotFoundException;
import com.AtmApi.ATM_BACKEND.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
            
    public AccountService(AccountRepository accountRepository ,TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Account getAccount(String accNum) {
        return accountRepository.findById(accNum)
                .orElseThrow(() ->
                    new AccountNotFoundException("Account not found"));
}
    public Account updateAccount(String accNum, Account account) {

        Account existingAccount = accountRepository.findById(accNum)
                .orElseThrow(() ->
                    new AccountNotFoundException("Account not found"));

        if(existingAccount == null) {
            return null;
        }
        existingAccount.setPinNum(account.getPinNum());
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    public boolean deleteAccount(String accNum) {

        if (!accountRepository.existsById(accNum)) {
            return false;
        }
        accountRepository.deleteById(accNum);
        return true;
    }
    
    @Transactional
    public Account deposit(String accNum, double amount) {

        Account account = accountRepository.findById(accNum).orElse(null);

        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }
        if (amount <= 0) {
            throw new AccountNotFoundException("Deposit amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);

        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getBalance());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
        return savedAccount;
}
    @Transactional
    public Account withdraw(String accNum, double amount) {
        Account account = accountRepository.findById(accNum).orElse(null);

        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }
        if (amount <= 0) {
            throw new AccountNotFoundException("Withdrawal amount must be greater than zero");
         }

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getBalance());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return savedAccount;
}
    public List<TransactionResponse> getTransactionHistory(String accNum) {

        List<Transaction> transactions = transactionRepository.findByAccount_AccNum(accNum);

        return transactions.stream()
            .map(transaction -> new TransactionResponse(
            transaction.getTransactionId(),
            transaction.getTransactionType(),
            transaction.getAmount(),
            transaction.getBalanceAfter(),
            transaction.getTransactionDate() ))
            .collect(Collectors.toList());
    }        
    @Transactional
    public boolean transfer(String fromAccNum, String toAccNum, double amount) {

        Account fromAccount = accountRepository.findById(fromAccNum).orElse(null);
        Account toAccount = accountRepository.findById(toAccNum).orElse(null);

        if (fromAccount == null) {
            throw new AccountNotFoundException("Source account not found");
        }

        if (toAccount == null) {
            throw new AccountNotFoundException("Account not found");
         }
        if (fromAccNum.equals(toAccNum)) {
            throw new AccountNotFoundException("Cannot transfer to the same account");
        }

        if (amount <= 0) {
            throw new AccountNotFoundException("Transfer amount must be greater than zero");
        }

        if(fromAccount.getBalance() < amount) {
            throw new AccountNotFoundException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);

        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccount(fromAccount);
        senderTransaction.setTransactionType("TRANSFER_SENT");
        senderTransaction.setAmount(amount);
        senderTransaction.setBalanceAfter(fromAccount.getBalance());
        senderTransaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(senderTransaction);

        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setAccount(toAccount);
        receiverTransaction.setTransactionType("TRANSFER_RECEIVED");
        receiverTransaction.setAmount(amount);
        receiverTransaction.setBalanceAfter(toAccount.getBalance());
        receiverTransaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(receiverTransaction);

        return true;
}
    
}