package com.digital.bank.api.validation.account;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.exception.account.AccountNotFoundException;
import com.digital.bank.api.exception.account.InvalidAccountDebitException;
import com.digital.bank.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IsFundsEnoughToDebitValidation implements AbstractAccountValidation {

    private final AccountRepository accountRepository;

    @Override
    public void validate(TransferAmountDTO transferAmountDTO) {
        Account account = accountRepository.findByNumber(UUID.fromString(transferAmountDTO.accountNumber()))
                .orElseThrow(() -> new AccountNotFoundException(UUID.fromString(transferAmountDTO.accountNumber())));

        if (account.getAmount().compareTo(transferAmountDTO.amount()) < 0) {
            throw new InvalidAccountDebitException();
        }
    }
}
