package com.example.corebanksystem.dtos;

import com.example.corebanksystem.utis.AccountStatus;
import com.example.corebanksystem.utis.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountUpdateRequestDto {
    private BigDecimal balance;
    private AccountType accountType;
    private AccountStatus status;
}
