package com.digital.bank.api.generator.dto;

import com.digital.bank.api.dto.TransferAmountDTO;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

public class TransferAmountDTOGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static TransferAmountDTO getTransferAmountDTO(){
        return new TransferAmountDTO(
                UUID.randomUUID().toString(),
                BigDecimal.valueOf(secureRandom.nextDouble())
        );
    }

    public static TransferAmountDTO getTransferAmountDTO(
            String accountNumber,
            BigDecimal amount
    ){
        return new TransferAmountDTO(
                accountNumber,
                amount
        );
    }

    public static TransferAmountDTO getTransferAmountDTO(
            String accountNumber
    ){
        return new TransferAmountDTO(
                accountNumber,
                BigDecimal.valueOf(secureRandom.nextDouble())
        );
    }

    public static TransferAmountDTO getTransferAmountDTO(
            BigDecimal amount
    ){
        return new TransferAmountDTO(
                UUID.randomUUID().toString(),
                amount
        );
    }

}
