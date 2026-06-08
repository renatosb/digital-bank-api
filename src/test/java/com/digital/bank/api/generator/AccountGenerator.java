package com.digital.bank.api.generator;

import com.digital.bank.api.entity.Account;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AccountGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static Account getAccount() {
        Account account = new Account();

        account.setNumber(UUID.randomUUID());
        account.setClient("Client" + secureRandom.nextInt());
        account.setAmount(BigDecimal.valueOf(secureRandom.nextDouble()));

        return account;
    }

    public static Account getAccount(
            UUID uuid,
            String client,
            BigDecimal amount
    ) {
        Account account = new Account();

        account.setNumber(UUID.randomUUID());
        account.setClient("Client" +secureRandom.nextInt());
        account.setAmount(BigDecimal.valueOf(secureRandom.nextDouble()));

        return account;
    }

    public static void expectedAccount(Account account, ResultActions result) {
        try {
            result.andExpectAll(
                    jsonPath("$.id")
                            .value(account.getId()),
                    jsonPath("$.client")
                            .value(account.getClient()),
                    jsonPath("$.number")
                            .value(account.getNumber().toString()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
