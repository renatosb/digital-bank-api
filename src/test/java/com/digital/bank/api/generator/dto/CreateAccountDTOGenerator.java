package com.digital.bank.api.generator.dto;

import com.digital.bank.api.dto.CreateAccountDTO;

import java.math.BigDecimal;
import java.security.SecureRandom;

public class CreateAccountDTOGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static CreateAccountDTO getCreateAccountDTO() {
        return new CreateAccountDTO(
                "Client" + secureRandom.nextInt(),
                BigDecimal.valueOf(secureRandom.nextDouble())
        );
    }

    public static CreateAccountDTO getCreateAccountDTO(
            String client,
            BigDecimal amount
    ) {
        return new CreateAccountDTO(
                client,
                amount
        );
    }
}
