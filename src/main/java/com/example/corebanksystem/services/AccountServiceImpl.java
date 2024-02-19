package com.example.corebanksystem.services;

import com.example.corebanksystem.dtos.AccountResponseDto;
import com.example.corebanksystem.dtos.AccountRequestDto;
import com.example.corebanksystem.dtos.AccountUpdateRequestDto;
import com.example.corebanksystem.exceptions.AccountCreationException;
import com.example.corebanksystem.exceptions.AccountDetailsDoesNotExist;
import com.example.corebanksystem.model.Account;
import com.example.corebanksystem.model.Customer;
import com.example.corebanksystem.repository.AccountRepository;
import com.example.corebanksystem.utis.AccountStatus;
import com.example.corebanksystem.utis.UtilsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    private ModelMapper modelMapper ;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

//    public Account getAccountByAccountNumber(String accountNumber) {
//        return accountRepository.findByAccountNumber(accountNumber)
//                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
//    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountDto) throws AccountCreationException {
        Account account = new Account();
        Customer customer = new Customer();
        String accountNumber = UtilsGenerator.generateAccountNumber();
        account.setAccountNumber(accountNumber);
        account.setStatus(AccountStatus.ACTIVE);
        account.setType(accountDto.getType);

        customer.setEmail(accountDto.getCustomerRequestDto().getEmail());
        customer.setFirstName(accountDto.getCustomerRequestDto().getFirstName());
        customer.setLastName(accountDto.getCustomerRequestDto().getLastName());
        customer.setDateOfBirth(accountDto.getCustomerRequestDto().getDateOfBirth());
        customer.setPhoneNumber(accountDto.getCustomerRequestDto().getPhoneNumber());
        customer.setPassword(accountDto.getCustomerRequestDto().getPassword());
        account.setCustomer(customer);

        // Set other fields from DTO

        // Perform any additional logic before saving the account
        Account createdAccount = accountRepository.save(account);

        // Create AccountCreationResponseDto and populate with data from the created account
        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setAccountNumber(createdAccount.getAccountNumber());
        responseDto.setAccountType(createdAccount.getType());
        responseDto.setStatus(createdAccount.getStatus());
        responseDto.setBalance(createdAccount.getBalance());
        responseDto.setAccountId(createdAccount.getId());
        // Set other fields from the created account if needed

        return responseDto;
    }

    @Override
    public Optional<AccountResponseDto> getAccountDetail(String accountNumber) throws AccountDetailsDoesNotExist {

        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountDetailsDoesNotExist("")));
    }

    @Override
    public AccountResponseDto updateAccountDetails(String accountId, AccountUpdateRequestDto updateRequest) throws AccountDetailsDoesNotExist {

        // Retrieve the account from the repository
        Account existingAccount = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new AccountDetailsDoesNotExist("Account not found for account number: " + accountId));

        // Update account details
        existingAccount.setBalance(updateRequest.getBalance());
        existingAccount.setType(updateRequest.getAccountType());
        existingAccount.setStatus(updateRequest.getStatus());
//        existingAccount.set
        // Update other account details as needed

        // Save the updated account
        Account updatedAccount = accountRepository.save(existingAccount);

        AccountResponseDto responseDto = modelMapper.map(updatedAccount, AccountResponseDto.class);

        // Map the updated account to DTO and return
        return responseDto;
    }
    @Override
    public AccountResponseDto closeAccount(String accountId) throws AccountDetailsDoesNotExist {
        Account existingAccount = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new AccountDetailsDoesNotExist("Account not found for account number: " + accountId));
        existingAccount.setStatus(AccountStatus.CLOSED);
        Account updatedAccount = accountRepository.save(existingAccount);
        AccountResponseDto responseDto = modelMapper.map(updatedAccount, AccountResponseDto.class);


return responseDto;
    }
}
