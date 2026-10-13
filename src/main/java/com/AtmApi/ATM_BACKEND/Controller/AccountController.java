package com.AtmApi.ATM_BACKEND.Controller;

import com.AtmApi.ATM_BACKEND.DTO.TransactionResponse;
import com.AtmApi.ATM_BACKEND.Entity.Account;
import com.AtmApi.ATM_BACKEND.Service.AccountService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
            return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
  
    @GetMapping("/{accNum}")
    public Account getAccount(@PathVariable String accNum) {
        return accountService.getAccount(accNum);
    }
    
    @PutMapping("/{accNum}")
    public Account updateAccount(@PathVariable String accNum,@RequestBody Account account) {
        return accountService.updateAccount(accNum, account);
    }
   
    @DeleteMapping("/{accNum}")
    public String deleteAccount(@PathVariable String accNum) {

        boolean deleted = accountService.deleteAccount(accNum);
        if (deleted) {
            return "Account deleted successfully";
        }
        return "Account not found";
    }
    //ADD DEPOSIT  (ENDPOINTS)
    @PostMapping("/{accNum}/deposit")
        public Account deposit(@PathVariable String accNum,@RequestParam double amount) {
            return accountService.deposit(accNum, amount);
    }
        
    //ADD WITHDRAW (ENDPOINTS)
    @PostMapping("/{accNum}/withdraw")
    public Account withdraw(@PathVariable String accNum,@RequestParam double amount) {
        return accountService.withdraw(accNum, amount);
        }
    
        //ADD TRANSFER (ENDPOINTS)
    @PostMapping("/{accNum}/transfer")
    public Map<String, Object> transfer(@PathVariable String accNum, @RequestParam String toAccNum,
        @RequestParam double amount) {

        accountService.transfer(accNum, toAccNum, amount);
        return Map.of(
            "message", "Transfer successful",
            "fromAccount", accNum,
            "toAccount", toAccNum,
            "amount", amount);
}
    @GetMapping("/{accNum}/transactions")
    public List<TransactionResponse> getTransactionHistory(@PathVariable String accNum) {
        return accountService.getTransactionHistory(accNum);
}

}