package com.example.corebanksystem.exceptions;

public class AccountDetailsDoesNotExist extends Exception{
    public AccountDetailsDoesNotExist() {
        super();
    }

    public AccountDetailsDoesNotExist(String message) {
        super(message);
    }
}
