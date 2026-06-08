package com.digital.bank.api.generator;

import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TransactionGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static Transaction getTransaction() {
        Transaction transaction = new Transaction();

        transaction.setSourceAccount(AccountGenerator.getAccount());
        transaction.setDestinationAccount(AccountGenerator.getAccount());
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

        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(amount);
        transaction.setCreatedAt(createdAt);

        return transaction;
    }
}
