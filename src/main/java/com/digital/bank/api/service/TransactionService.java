package com.digital.bank.api.service;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.entity.Transaction;
import com.digital.bank.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionValidationService transactionValidationService;
    private final AccountService accountService;

    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll().stream().map(TransactionDTO::toDTO).toList();
    }

    @Transactional
    public TransactionDTO performTransaction(PerformTransactionDTO performTransactionDTO) {
        AccountDTO sourceAccount = accountService.getAccountByAccountNumber(performTransactionDTO.sourceAccountNumber().toString());
        AccountDTO destinationAccount = accountService.getAccountByAccountNumber(performTransactionDTO.destinationAccountNumber().toString());

        transactionValidationService.validate(performTransactionDTO);

        TransferAmountDTO fromSourceAccount = new TransferAmountDTO(
                sourceAccount.number().toString(),
                performTransactionDTO.amount()
        );
        sourceAccount = accountService.subtractFunds(fromSourceAccount);

        TransferAmountDTO toDestinationAccount = new TransferAmountDTO(
                destinationAccount.number().toString(),
                performTransactionDTO.amount()
        );
        destinationAccount = accountService.addFunds(toDestinationAccount);

        log.info("Notifying Client {} ", destinationAccount.client());

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount.toEntity());
        transaction.setDestinationAccount(destinationAccount.toEntity());
        transaction.setAmount(performTransactionDTO.amount());

        return TransactionDTO.toDTO(transactionRepository.save(transaction));
    }
}
