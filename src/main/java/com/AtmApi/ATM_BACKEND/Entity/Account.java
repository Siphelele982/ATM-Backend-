package com.AtmApi.ATM_BACKEND.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "acc_num")
    @JsonProperty("acc_num")   
    private String accNum;

    @Column(name = "pin_num")
    @JsonProperty(value = "pin_num", access = JsonProperty.Access.WRITE_ONLY)
    private String pinNum;
    
    @Column(name = "balance")
    @JsonProperty("balance")  
    private double balance;

    public Account() {
    }

    public Account(String accNum, String pinNum, double balance) {
        this.accNum = accNum;
        this.pinNum = pinNum;
        this.balance = balance;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getPinNum() {
        return pinNum;
    }

    public void setPinNum(String pinNum) {
        this.pinNum = pinNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}