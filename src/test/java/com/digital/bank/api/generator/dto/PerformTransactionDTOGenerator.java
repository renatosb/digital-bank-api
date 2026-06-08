package com.digital.bank.api.generator.dto;

import com.digital.bank.api.dto.PerformTransactionDTO;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

public class PerformTransactionDTOGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static PerformTransactionDTO getPerformTransactionDTO() {
        return new PerformTransactionDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.valueOf(secureRandom.nextDouble())
        );
    }

    public static PerformTransactionDTO performTransactionDTO(
            UUID sourceAccountNumber,
            UUID destinationAccountNumber,
            BigDecimal amount
    ) {
        return new PerformTransactionDTO(
                sourceAccountNumber,
                destinationAccountNumber,
                amount
        );
    }
}
