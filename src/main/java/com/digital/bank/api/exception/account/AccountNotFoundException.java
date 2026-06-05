package com.digital.bank.api.exception.account;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountType) {
        super(accountType +" Not Found");
    }

    public AccountNotFoundException(UUID accountNumber) {
        super("Account Not Found for account number " + accountNumber);
    }
}
