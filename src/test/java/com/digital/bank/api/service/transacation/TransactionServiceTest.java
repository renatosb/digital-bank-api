package com.digital.bank.api.service.transacation;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import com.digital.bank.api.generator.AccountGenerator;
import com.digital.bank.api.generator.TransactionGenerator;
import com.digital.bank.api.repository.AccountRepository;
import com.digital.bank.api.repository.TransactionRepository;
import com.digital.bank.api.service.AccountService;
import com.digital.bank.api.service.TransactionService;
import com.digital.bank.api.service.TransactionValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionValidationService transactionValidationService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Should get all transactions")
    void getAll() {

        List<Transaction> transactions = List.of(
                TransactionGenerator.getTransaction(),
                TransactionGenerator.getTransaction(),
                TransactionGenerator.getTransaction()
        );

        when(transactionRepository.findAll()).thenReturn(transactions);
        List<TransactionDTO> result = transactionService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), transactions.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should no transactions")
    void getEmptyTransaction() {

        List<Transaction> transactions = List.of();

        when(transactionRepository.findAll()).thenReturn(transactions);
        List<TransactionDTO> result = transactionService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should perform transaction")
    void performTransaction() {
        Account sourceAccount = AccountGenerator.getAccount();
        sourceAccount.setAmount(BigDecimal.TEN);

        Account destinationAccount = AccountGenerator.getAccount();
        destinationAccount.setAmount(BigDecimal.TEN);

        PerformTransactionDTO performTransactionDTO = new PerformTransactionDTO(
                sourceAccount.getNumber(),
                destinationAccount.getNumber(),
                BigDecimal.ONE
        );

        when(accountService.getAccountByAccountNumber(performTransactionDTO.sourceAccountNumber().toString()))
                .thenReturn(AccountDTO.toDTO(sourceAccount));

        when(accountService.getAccountByAccountNumber(performTransactionDTO.destinationAccountNumber().toString()))
                .thenReturn(AccountDTO.toDTO(destinationAccount));

        sourceAccount.setAmount(sourceAccount.getAmount().subtract(performTransactionDTO.amount()));
        destinationAccount.setAmount(destinationAccount.getAmount().add(performTransactionDTO.amount()));

        when(accountService.subtractFunds(any(TransferAmountDTO.class)))
                .thenReturn(AccountDTO.toDTO(sourceAccount));

        when(accountService.addFunds(any(TransferAmountDTO.class)))
                .thenReturn(AccountDTO.toDTO(destinationAccount));

        Transaction transaction = TransactionGenerator.getTransaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(performTransactionDTO.amount());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.performTransaction(performTransactionDTO);
    }
}
