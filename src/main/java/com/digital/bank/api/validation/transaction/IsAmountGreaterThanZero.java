package com.digital.bank.api.validation.transaction;

import com.digital.bank.api.dto.TransactionDTO;
import com.digital.bank.api.exception.transaction.InvalidTransactionAmountException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IsAmountGreaterThanZero implements AbstractTransactionValidation {

    public void validate(TransactionDTO transactionDTO) {
        if(transactionDTO.amount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmountException();
        }
    }
}
