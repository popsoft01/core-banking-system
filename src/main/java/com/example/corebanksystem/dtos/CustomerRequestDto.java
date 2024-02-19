package com.example.corebanksystem.dtos;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CustomerRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
