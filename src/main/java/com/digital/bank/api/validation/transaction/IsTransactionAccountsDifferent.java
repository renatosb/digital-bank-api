package com.digital.bank.api.validation.transaction;

import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.exception.transaction.InvalidTransactionTransferException;
import org.springframework.stereotype.Service;

@Service
public class IsTransactionAccountsDifferent implements AbstractTransactionValidation {

    public void validate(PerformTransactionDTO performTransactionDTO) {
        if (performTransactionDTO.sourceAccountNumber()
                .equals(performTransactionDTO.destinationAccountNumber())) {
            throw new InvalidTransactionTransferException();
        }
    }
}
