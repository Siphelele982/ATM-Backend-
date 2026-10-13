package com.AtmApi.ATM_BACKEND.Repository;


import com.AtmApi.ATM_BACKEND.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}