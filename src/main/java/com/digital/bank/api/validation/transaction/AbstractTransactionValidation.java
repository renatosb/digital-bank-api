package com.digital.bank.api.validation.transaction;

import com.digital.bank.api.dto.TransactionDTO;

public interface AbstractTransactionValidation {
    public void validate(TransactionDTO transactionDTO);
}
