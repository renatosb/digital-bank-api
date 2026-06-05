package com.digital.bank.api.validation.account;

import com.digital.bank.api.dto.TransferAmountDTO;

public interface AbstractAccountValidation {
    public void validate(TransferAmountDTO transferAmountDTO);
}
