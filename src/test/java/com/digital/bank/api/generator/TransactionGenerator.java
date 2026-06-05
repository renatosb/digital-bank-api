package com.digital.bank.api.generator;

import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class TransactionGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static Transaction getTransaction() {
        Transaction transaction = new Transaction();

        transaction.setSourceAccountId(AccountGenerator.getAccount());
        transaction.setDestinationAccountId(AccountGenerator.getAccount());
        transaction.setAmount(BigDecimal.valueOf(secureRandom.nextDouble()));
        transaction.setCreatedAt(LocalDateTime.now());

        return transaction;
    }

    public static Transaction getTransaction(
            Account sourceAccount,
            Account destinationAccount,
            BigDecimal amount,
            LocalDateTime createdAt
    ) {
        Transaction transaction = new Transaction();

        transaction.setSourceAccountId(sourceAccount);
        transaction.setDestinationAccountId(destinationAccount);
        transaction.setAmount(amount);
        transaction.setCreatedAt(createdAt);

        return transaction;
    }
}
