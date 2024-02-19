package com.example.corebanksystem.controller;

import com.example.corebanksystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("api/v1/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/{accountNumber}")
    public ResponseEntity<String> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {

        // Call a service method to handle the deposit transaction
        boolean success = transactionService.deposit(accountNumber, amount);

        if (success) {
            return ResponseEntity.ok("Deposit successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deposit failed");
        }
    }

    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<String> withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {

        boolean success = transactionService.withdraw(accountNumber, amount);

        if (success) {
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Withdrawal failed");
        }
    }

    @PostMapping
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam BigDecimal amount) {

        boolean success = transactionService.transfer(fromAccountNumber, toAccountNumber, amount);

        if (success) {
            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transfer failed");
        }
    }
}
