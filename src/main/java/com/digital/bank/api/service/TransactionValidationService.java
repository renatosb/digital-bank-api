package com.digital.bank.api.service;

import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.validation.transaction.AbstractTransactionValidation;
import com.digital.bank.api.validation.transaction.IsAmountGreaterThanZero;
import com.digital.bank.api.validation.transaction.IsTransactionAccountsDifferent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionValidationService {

    private final IsAmountGreaterThanZero isAmountGreaterThanZero;
    private final IsTransactionAccountsDifferent isTransactionAccountsDifferent;

    public void validate(TransactionDTO transactionDTO) {
        List<AbstractTransactionValidation> validations = List.of(
                isAmountGreaterThanZero,
                isTransactionAccountsDifferent
        );

        for (AbstractTransactionValidation validation : validations) {
            validation.validate(transactionDTO);
        }
    }
}
