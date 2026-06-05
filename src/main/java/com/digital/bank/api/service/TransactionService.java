package com.digital.bank.api.service;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.entity.Transaction;
import com.digital.bank.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionValidationService transactionValidationService;

    private final AccountService accountService;

    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Transactional
    private void performTransaction(PerformTransactionDTO performTransactionDTO) {

    }

    private TransactionDTO toTransactionDTO(PerformTransactionDTO performTransactionDTO) {
        AccountDTO sourceAccount = accountService.getAccountByAccountNumber(performTransactionDTO.sourceAccountNumber().toString());
        AccountDTO destinationAccount = accountService.getAccountByAccountNumber(performTransactionDTO.destinationAccountNumber().toString());

        return null;
    }
}
