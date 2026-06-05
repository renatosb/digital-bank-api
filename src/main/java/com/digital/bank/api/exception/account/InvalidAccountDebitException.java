package com.digital.bank.api.exception.account;

public class InvalidAccountDebitException extends RuntimeException {
    public InvalidAccountDebitException() {
        super("Account has no enough funds to be debited");
    }
}
