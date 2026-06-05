package com.digital.bank.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PerformTransactionDTO(
        UUID sourceAccountNumber,
        UUID destinationAccountNumber,
        BigDecimal amount
) {
}
