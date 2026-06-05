package com.digital.bank.api.dto;

import java.math.BigDecimal;

public record TransferAmountDTO(
        String accountNumber,
        BigDecimal amount
) {
}
