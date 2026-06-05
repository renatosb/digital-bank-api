package com.digital.bank.api.exception.handler;

import java.time.LocalDateTime;

public record ErrorResponse(
        int Status,
        String message,
        LocalDateTime timestamp
) {
}