package com.digital.bank.api.dto;

import com.digital.bank.api.entity.Account;

import java.math.BigDecimal;

public record CreateAccountDTO(
        String client,
        BigDecimal amount
) {
    public Account toEntity(){
        Account account = new Account();

        account.setClient(client);
        account.setAmount(amount);

        return account;
    }
}
