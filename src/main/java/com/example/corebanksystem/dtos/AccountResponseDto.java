package com.example.corebanksystem.dtos;

import com.example.corebanksystem.utis.AccountStatus;
import com.example.corebanksystem.utis.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountResponseDto {
    private UUID accountId;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private AccountStatus status;
    private CustomerRequestDto customerRequestDto;
}
