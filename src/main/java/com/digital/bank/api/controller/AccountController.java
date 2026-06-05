package com.digital.bank.api.controller;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.CreateAccountDTO;
import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Accounts")
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account/{number}")
    @Operation(summary = "Get account by Account number UUID")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String number) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAccountByAccountNumber(number));
    }

    @GetMapping
    @Operation(summary = "Get All accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.findAll());
    }

    @PostMapping
    @Operation(summary = "Create an account")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(createAccountDTO));
    }
}
