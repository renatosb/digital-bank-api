package com.digital.bank.api.exception.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Transaction Not Found");
    }
}
