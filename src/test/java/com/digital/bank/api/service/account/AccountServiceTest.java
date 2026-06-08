package com.digital.bank.api.service.account;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.CreateAccountDTO;
import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import com.digital.bank.api.exception.account.AccountNotFoundException;
import com.digital.bank.api.generator.AccountGenerator;
import com.digital.bank.api.generator.TransactionGenerator;
import com.digital.bank.api.generator.dto.TransferAmountDTOGenerator;
import com.digital.bank.api.repository.AccountRepository;
import com.digital.bank.api.repository.TransactionRepository;
import com.digital.bank.api.service.AccountService;
import com.digital.bank.api.service.AccountValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountValidationService accountValidationService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("Should get account by account number")
    void getAccountByAccountNumber() {

        UUID accountUUID = UUID.randomUUID();

        Account account = AccountGenerator.getAccount();
        account.setNumber(accountUUID);

        when(accountRepository.findByNumber(accountUUID)).thenReturn(Optional.of(account));
        AccountDTO result = accountService.getAccountByAccountNumber(accountUUID.toString());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.number(), accountUUID);
        verify(accountRepository, times(1)).findByNumber(accountUUID);
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException when account not found")
    void getAccountByAccountWhenNumberNotFound() {
        UUID accountUUID = UUID.randomUUID();
        when(accountRepository.findByNumber(accountUUID)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccountByAccountNumber(accountUUID.toString()
                )
        );

        verify(accountRepository, times(1)).findByNumber(accountUUID);
    }

    @Test
    @DisplayName("Should get all transactions by account number")
    void getTransactionsByAccountNumber() {

        List<Transaction> transactions = List.of(
                TransactionGenerator.getTransaction(),
                TransactionGenerator.getTransaction(),
                TransactionGenerator.getTransaction()
        );

        when(accountRepository.findTransactionsByAccountNumber(any(UUID.class))).thenReturn(transactions);
        List<TransactionDTO> result = accountService.getTransactionsByAccountNumber(UUID.randomUUID().toString());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), transactions.size());
        verify(accountRepository, times(1)).findTransactionsByAccountNumber(any(UUID.class));
    }

    @Test
    @DisplayName("Should no transactions by account number")
    void getTransactionsByAccountNumberWhenIsEmpty() {
        when(accountRepository.findTransactionsByAccountNumber(any(UUID.class))).thenReturn(Collections.emptyList());
        List<TransactionDTO> result = accountService.getTransactionsByAccountNumber(UUID.randomUUID().toString());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        verify(accountRepository, times(1)).findTransactionsByAccountNumber(any(UUID.class));
    }

    @Test
    @DisplayName("Should get all accounts")
    void getAllAccounts() {
        List<Account> accounts = List.of
                (
                        AccountGenerator.getAccount(),
                        AccountGenerator.getAccount(),
                        AccountGenerator.getAccount(),
                        AccountGenerator.getAccount(),
                        AccountGenerator.getAccount()

                );
        when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountDTO> results = accountService.findAll();

        Assertions.assertEquals(results.size(), accounts.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return an empty list when no accounts exist")
    void getAllEmpty() {

        when(accountRepository.findAll()).thenReturn(List.of());

        List<AccountDTO> results = accountService.findAll();

        Assertions.assertTrue(results.isEmpty());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should create account")
    void createAccount() {
        Account account = AccountGenerator.getAccount();
        CreateAccountDTO createAccountDTO = new CreateAccountDTO(
                account.getClient(),
                account.getAmount()
        );

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDTO result = accountService.createAccount(createAccountDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.number(), account.getNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should add funds to account")
    void addFunds() {
        Account accountBefore = AccountGenerator.getAccount();
        TransferAmountDTO transferAmountDTO = TransferAmountDTOGenerator
                .getTransferAmountDTO(accountBefore.getNumber().toString());

        Account accountAfter = AccountGenerator.getAccount();
        accountAfter.setAmount(accountBefore.getAmount().add(transferAmountDTO.amount()));

        when(accountRepository.findByNumber(accountBefore.getNumber())).thenReturn(Optional.of(accountBefore));
        when(accountRepository.save(any(Account.class))).thenReturn(accountAfter);

        AccountDTO result = accountService.addFunds(transferAmountDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.number(), accountAfter.getNumber());

        Assertions.assertEquals(result.amount(), accountAfter.getAmount());

        verify(accountRepository, times(1)).findByNumber(accountBefore.getNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException when adding funds to non-existent account")
    void addFundsAccountNotFound() {
        TransferAmountDTO transferAmountDTO = TransferAmountDTOGenerator.getTransferAmountDTO();
        when(accountRepository.findByNumber(UUID.fromString(transferAmountDTO.accountNumber())))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.addFunds(transferAmountDTO)
        );
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Should subtract funds from account")
    void subtractFunds() {

        Account accountBefore = AccountGenerator.getAccount();
        TransferAmountDTO transferAmountDTO = TransferAmountDTOGenerator
                .getTransferAmountDTO(
                        accountBefore.getNumber().toString(),
                        accountBefore.getAmount().subtract(BigDecimal.ONE)
                );

        Account accountAfter = AccountGenerator.getAccount();
        accountAfter.setAmount(accountBefore.getAmount().subtract(transferAmountDTO.amount()));

        when(accountRepository.findByNumber(accountBefore.getNumber())).thenReturn(Optional.of(accountBefore));
        when(accountRepository.save(any(Account.class))).thenReturn(accountAfter);

        AccountDTO result = accountService.subtractFunds(transferAmountDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.number(), accountAfter.getNumber());
        Assertions.assertEquals(result.amount(), accountAfter.getAmount());
        verify(accountRepository, times(1)).findByNumber(accountBefore.getNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException when subtracting funds from non-existent account")
    void subtractFundsAccountNotFound() {
        TransferAmountDTO transferAmountDTO = TransferAmountDTOGenerator.getTransferAmountDTO();
        when(accountRepository.findByNumber(UUID.fromString(transferAmountDTO.accountNumber())))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.subtractFunds(transferAmountDTO)
        );
        verify(accountRepository, never()).save(any(Account.class));
    }
}
