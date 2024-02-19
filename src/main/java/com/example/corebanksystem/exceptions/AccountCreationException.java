package com.example.corebanksystem.exceptions;

public class AccountCreationException extends Exception {
    public AccountCreationException() {
        super();
    }

    public AccountCreationException(String message) {
        super(message);
    }

    public AccountCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
