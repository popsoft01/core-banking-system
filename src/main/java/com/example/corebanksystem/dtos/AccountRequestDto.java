package com.example.corebanksystem.dtos;

import com.example.corebanksystem.utis.AccountStatus;
import com.example.corebanksystem.utis.AccountType;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountRequestDto {
    public AccountType getType;
    private BigDecimal balance;
    private AccountStatus status;
    private CustomerRequestDto customerRequestDto;



}
