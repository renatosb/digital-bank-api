package com.digital.bank.api.validation.transaction;

import com.digital.bank.api.dto.PerformTransactionDTO;

public interface AbstractTransactionValidation {
    public void validate(PerformTransactionDTO performTransactionDTO);
}
