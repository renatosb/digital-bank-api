package com.digital.bank.api.dto;

import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;

import java.math.BigDecimal;

public record TransactionDTO(
        Long id,
        Account sourceAccount,
        Account destinationAccount,
        BigDecimal amount
) {
    public Transaction toEntity() {
        Transaction transaction = new Transaction();

        transaction.setId(id);
        transaction.setSourceAccountId(sourceAccount);
        transaction.setDestinationAccountId(destinationAccount);
        transaction.setAmount(amount);

        return transaction;
    }
}
