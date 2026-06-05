package com.digital.bank.api.validation.transaction;

import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.exception.transaction.InvalidTransactionAmountException;
import com.digital.bank.api.exception.transaction.InvalidTransactionTransferException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IsTransactionAccountsDifferent implements AbstractTransactionValidation {

    public void validate(TransactionDTO transactionDTO) {
        if (transactionDTO.sourceAccount().getId()
                .equals(transactionDTO.destinationAccount().getId())) {
            throw new InvalidTransactionTransferException();
        }
    }
}
