package com.digital.bank.api.dto;

import com.digital.bank.api.entity.Account;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDTO(
        Long id,
        String client,
        BigDecimal amount,
        UUID number
) {
    public Account toEntity(){
        Account account = new Account();

        account.setId(id);
        account.setClient(client);
        account.setAmount(amount);
        account.setNumber(number);

        return account;
    }

    public static AccountDTO toDTO(Account account){
        return new AccountDTO(
                account.getId(),
                account.getClient(),
                account.getAmount(),
                account.getNumber());
    }
}
