package com.digital.bank.api.exception.transaction;

public class InvalidTransactionTransferException extends RuntimeException {
    public InvalidTransactionTransferException() {
        super("The source account must be different of destination");
    }
}
