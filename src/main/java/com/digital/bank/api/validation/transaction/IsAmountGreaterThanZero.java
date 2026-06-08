package com.digital.bank.api.validation.transaction;

import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.exception.transaction.InvalidTransactionAmountException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IsAmountGreaterThanZero implements AbstractTransactionValidation {

    public void validate(PerformTransactionDTO performTransactionDTO) {
        if(performTransactionDTO.amount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmountException();
        }
    }
}
