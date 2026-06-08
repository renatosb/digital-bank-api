package com.digital.bank.api.controller;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.CreateAccountDTO;
import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Transactions")
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get All Transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.findAll());
    }

    @PostMapping
    @Operation(summary = "Perform a transaction")
    public ResponseEntity<TransactionDTO> performTransaction(@RequestBody PerformTransactionDTO performTransactionDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.performTransaction(performTransactionDTO));
    }
}
