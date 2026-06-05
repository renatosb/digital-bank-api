package com.digital.bank.api.exception.transaction;

public class InvalidTransactionAmountException extends RuntimeException{
    public InvalidTransactionAmountException() {
        super("The transfer amount must be greater than 0");
    }
}
