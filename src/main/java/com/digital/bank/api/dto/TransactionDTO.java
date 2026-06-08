package com.digital.bank.api.dto;

import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        Account sourceAccount,
        Account destinationAccount,
        BigDecimal amount,
        LocalDateTime createAt
) {
    public Transaction toEntity() {
        Transaction transaction = new Transaction();

        transaction.setId(id);
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(amount);

        return transaction;
    }

    public static TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId() ,
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getAmount(),
                transaction.getCreatedAt()
        );
    }
}
