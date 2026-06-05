package com.digital.bank.api.exception.handler;

import com.digital.bank.api.exception.transaction.InvalidTransactionAmountException;
import com.digital.bank.api.exception.transaction.InvalidTransactionTransferException;
import com.digital.bank.api.exception.transaction.TransactionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class TransactionControllerExceptionHandler {

    @ExceptionHandler(value = InvalidTransactionAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransactionAmountException
            (InvalidTransactionAmountException ex) {
        log.error("Invalid Transaction Amount: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));

    }

    @ExceptionHandler(value = InvalidTransactionTransferException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransactionTransferException
            (InvalidTransactionTransferException ex) {
        log.error("Invalid Transaction Transfer: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException
            (TransactionNotFoundException ex) {
        log.error("Transaction not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
