package com.example.corebanksystem.repository;

import com.example.corebanksystem.dtos.AccountResponseDto;
import com.example.corebanksystem.exceptions.AccountDetailsDoesNotExist;
import com.example.corebanksystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<AccountResponseDto> findByAccountNumber(String accountNumber) throws AccountDetailsDoesNotExist;
//   Account findByAccountNumber( String accountNumber);

}
