package com.example.corebanksystem.controller;

import com.example.corebanksystem.dtos.AccountResponseDto;
import com.example.corebanksystem.dtos.AccountRequestDto;
import com.example.corebanksystem.dtos.AccountUpdateRequestDto;
import com.example.corebanksystem.exceptions.AccountCreationException;
import com.example.corebanksystem.exceptions.AccountDetailsDoesNotExist;
import com.example.corebanksystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto account) {
        AccountResponseDto createdAccount = null;
        try {
             createdAccount = accountService.createAccount(account);

        } catch (AccountCreationException e) {
            e.getMessage();
        }

        return ResponseEntity.ok(createdAccount);
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Optional<AccountResponseDto>> getAccountDetais(@PathVariable final String accountNumber){
        Optional<AccountResponseDto> accountCreationResponseDto = null;
        try {
          accountCreationResponseDto =  accountService.getAccountDetail(accountNumber);
        }catch (AccountDetailsDoesNotExist e){
            e.getMessage();
        }
        return ResponseEntity.ok(accountCreationResponseDto);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable final String accountId, @RequestBody AccountUpdateRequestDto updateRequestDto){
        AccountResponseDto updateAccountRequest = null;
        try {
            updateAccountRequest = accountService.updateAccountDetails(accountId,updateRequestDto);

        }catch (AccountDetailsDoesNotExist e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(updateAccountRequest);
    }

    @PutMapping("/close/{accountId}")
    public ResponseEntity<AccountResponseDto> closeAccount(@PathVariable final String accountId){
        AccountResponseDto updateAccountRequest = null;
        try {
            updateAccountRequest = accountService.closeAccount(accountId);

        }catch (AccountDetailsDoesNotExist e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(updateAccountRequest);
    }
}
