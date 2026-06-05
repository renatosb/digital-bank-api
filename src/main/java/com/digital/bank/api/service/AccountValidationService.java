package com.digital.bank.api.service;

import com.digital.bank.api.dto.TransferAmountDTO;
import com.digital.bank.api.validation.account.AbstractAccountValidation;
import com.digital.bank.api.validation.account.IsFundsEnoughToDebitValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountValidationService {

    private final IsFundsEnoughToDebitValidation isFundsEnoughToDebitValidation;

    public void validateSubtract(TransferAmountDTO transferAmountDTO) {
        List<AbstractAccountValidation> validations = List.of(
                isFundsEnoughToDebitValidation
        );

        for (AbstractAccountValidation validation : validations) {
            validation.validate(transferAmountDTO);
        }
    }
}
