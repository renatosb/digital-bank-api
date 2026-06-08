package com.digital.bank.api.service;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.CreateAccountDTO;
import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.exception.account.AccountNotFoundException;
import com.digital.bank.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountValidationService accountValidationService;

    @Transactional(readOnly = true)
    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        return AccountDTO.toDTO(accountRepository.findByNumber(UUID.fromString(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException(UUID.fromString(accountNumber))));
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
        return accountRepository.findTransactionsByAccountNumber(UUID.fromString(accountNumber))
                .stream().map(TransactionDTO::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream().map(AccountDTO::toDTO).toList();
    }

    @Transactional
    public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
        return AccountDTO.toDTO(accountRepository.save(createAccountDTO.toEntity()));
    }

    @Transactional
    public AccountDTO addFunds(TransferAmountDTO transferAmountDTO) {

        Account account = getAccountByAccountNumber(transferAmountDTO.accountNumber()).toEntity();
        BigDecimal added = (account.getAmount().add(transferAmountDTO.amount()));
        account.setAmount(added);

        log.info("Added amount to the account {}, new value: {}", account.getNumber(), added);

        return AccountDTO.toDTO(accountRepository.save(account));
    }

    @Transactional
    public AccountDTO   subtractFunds(TransferAmountDTO transferAmountDTO) {

        accountValidationService.validateSubtract(transferAmountDTO);

        Account account = getAccountByAccountNumber(transferAmountDTO.accountNumber()).toEntity();
        BigDecimal subtracted = (account.getAmount().subtract(transferAmountDTO.amount()));
        account.setAmount(subtracted);

        log.info("Subtracted amount to the account {}, new value: {}", account.getNumber(), subtracted);

        return AccountDTO.toDTO(accountRepository.save(account));
    }
}
