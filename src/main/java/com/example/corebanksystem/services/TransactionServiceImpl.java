package com.example.corebanksystem.services;

import com.example.corebanksystem.dtos.AccountResponseDto;
import com.example.corebanksystem.dtos.TransactionType;
import com.example.corebanksystem.exceptions.AccountDetailsDoesNotExist;
import com.example.corebanksystem.exceptions.InsufficientFundsException;
import com.example.corebanksystem.model.Account;
import com.example.corebanksystem.model.Transaction;
import com.example.corebanksystem.repository.AccountRepository;
import com.example.corebanksystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public boolean deposit(String accountNumber, BigDecimal amount) {
        try {
            // Retrieve the account from the repository
            AccountResponseDto accountDto = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
            Account account = null;
            if (accountDto != null) {
                account = modelMapper.map(accountDto,Account.class);
            } else {
                throw new AccountDetailsDoesNotExist("Account does not exist");
            }
            // Create a new deposit transaction
            Transaction depositTransaction = new Transaction();
            depositTransaction.setAccount(account);
            depositTransaction.setAmount(amount);
            depositTransaction.setTransactionType(TransactionType.DEPOSIT);
            depositTransaction.setTransactionDate(LocalDateTime.now());

            // Update the account balance
            account.setBalance(account.getBalance().add(amount));

            // Save the transaction and update the account
            transactionRepository.save(depositTransaction);
            accountRepository.save(account);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean withdraw(String accountNumber, BigDecimal amount) {
        try {
            AccountResponseDto accountResponseDto = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            Account account = null;
            if (accountResponseDto != null) {
                account = modelMapper.map(accountResponseDto,Account.class);
            } else {
                throw new AccountDetailsDoesNotExist("Account does not exist");
            }

            // Check if account balance is sufficient for withdrawal
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }

            // Create a withdrawal transaction
            Transaction withdrawalTransaction = new Transaction();
            withdrawalTransaction.setAccount(account);
            withdrawalTransaction.setAmount(amount.negate()); // Withdrawal amount is negative
            withdrawalTransaction.setTransactionType(TransactionType.WITHDRAWAL);
            withdrawalTransaction.setTransactionDate(LocalDateTime.now());

            // Update the account balance
            account.setBalance(account.getBalance().subtract(amount));

            // Save the transaction and update the account
            transactionRepository.save(withdrawalTransaction);
            accountRepository.save(account);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Transactional
    public boolean transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        try {
            // Retrieve accounts involved in the transfer
            AccountResponseDto fromAccountResponse = accountRepository.findByAccountNumber(fromAccountNumber)
                    .orElseThrow(() -> new RuntimeException("From Account not found"));
            AccountResponseDto toAccountResponse = accountRepository.findByAccountNumber(toAccountNumber)
                    .orElseThrow(() -> new RuntimeException("To Account not found"));
            Account toAccount = null;
            Account fromAccount = null;

            if (fromAccountResponse != null ) {
                fromAccount = modelMapper.map(fromAccountResponse,Account.class);
            } else {
                throw new AccountDetailsDoesNotExist("Sender Account does not exist");
            }
            if (toAccountResponse != null ) {
                toAccount = modelMapper.map(toAccountResponse,Account.class);
            } else {
                throw new AccountDetailsDoesNotExist("Receiver Account does not exist");
            }

            // Check if fromAccount has sufficient balance for transfer
            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds for transfer");
            }

            // Create a withdrawal transaction from fromAccount
            Transaction withdrawalTransaction = new Transaction();
            withdrawalTransaction.setAccount(fromAccount);
            withdrawalTransaction.setAmount(amount.negate()); // Withdrawal amount is negative
            withdrawalTransaction.setTransactionType(TransactionType.TRANSFER);
            withdrawalTransaction.setTransactionDate(LocalDateTime.now());

            // Create a deposit transaction to toAccount
            Transaction depositTransaction = new Transaction();
            depositTransaction.setAccount(toAccount);
            depositTransaction.setAmount(amount);
            depositTransaction.setTransactionType(TransactionType.TRANSFER);
            depositTransaction.setTransactionDate(LocalDateTime.now());

            // Update account balances
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            // Save transactions and update accounts
            transactionRepository.save(withdrawalTransaction);
            transactionRepository.save(depositTransaction);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
