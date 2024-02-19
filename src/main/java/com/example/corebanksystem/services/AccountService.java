package com.example.corebanksystem.services;

import com.example.corebanksystem.dtos.AccountResponseDto;
import com.example.corebanksystem.dtos.AccountRequestDto;
import com.example.corebanksystem.dtos.AccountUpdateRequestDto;
import com.example.corebanksystem.exceptions.AccountDetailsDoesNotExist;
import com.example.corebanksystem.exceptions.AccountCreationException;

import java.util.Optional;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto account) throws AccountCreationException;
    Optional<AccountResponseDto> getAccountDetail(String accountNumber) throws AccountDetailsDoesNotExist;

    AccountResponseDto updateAccountDetails(String accountId, AccountUpdateRequestDto updateRequest) throws AccountDetailsDoesNotExist ;

    AccountResponseDto closeAccount(String accountId) throws AccountDetailsDoesNotExist;


    }
