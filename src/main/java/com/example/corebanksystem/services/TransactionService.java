package com.example.corebanksystem.services;

import java.math.BigDecimal;

public interface TransactionService {
    boolean deposit(String accountNumber, BigDecimal amount);

    boolean withdraw(String accountNumber, BigDecimal amount);

    boolean transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount);
//    withdraw();
}
