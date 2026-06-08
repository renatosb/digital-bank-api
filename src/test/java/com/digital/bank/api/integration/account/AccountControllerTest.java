package com.digital.bank.api.integration.account;

import com.digital.bank.api.dto.CreateAccountDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import com.digital.bank.api.generator.AccountGenerator;
import com.digital.bank.api.generator.TransactionGenerator;
import com.digital.bank.api.generator.dto.CreateAccountDTOGenerator;
import com.digital.bank.api.integration.BaseControllerTest;
import com.digital.bank.api.repository.AccountRepository;
import com.digital.bank.api.repository.TransactionRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setup() {
        transactionRepository.deleteAll();
        transactionRepository.flush();

        accountRepository.deleteAll();
        accountRepository.flush();
    }

    @Test
    @DisplayName("Should get account by account number")
    void getAccountByNumber() {
        Account account = accountRepository.saveAndFlush(
                AccountGenerator.getAccount()
        );

        String endpoint = "/api/v1/accounts/" + account.getNumber();
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        AccountGenerator.expectedAccount(account, result);
    }

    @Test
    @DisplayName("Should get account by account number checking round precision")
    void getAccountByNumberPrecisionCheck() {
        Account account = AccountGenerator.getAccount();
        account.setAmount(BigDecimal.valueOf(0.25));
        accountRepository.saveAndFlush(account);

        String endpoint = "/api/v1/accounts/" + account.getNumber();
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        AccountGenerator.expectedAccount(account, result);
    }

    @Test
    @DisplayName("Should get all transactions by account number")
    void getAccountsTransactionsByAccountNumber() {
        Account account = accountRepository.saveAndFlush(AccountGenerator.getAccount());

        Transaction transactionOne = TransactionGenerator.getTransaction();
        transactionOne.setSourceAccount(account);
        transactionOne.setDestinationAccount(accountRepository.saveAndFlush(AccountGenerator.getAccount()));

        Transaction transactionTwo = TransactionGenerator.getTransaction();
        transactionTwo.setSourceAccount(accountRepository.saveAndFlush(AccountGenerator.getAccount()));
        transactionTwo.setDestinationAccount(account);

        List<Transaction> transactionList = List.of(
                transactionOne,
                transactionTwo
        );
        transactionList.forEach(t -> transactionRepository.saveAndFlush(t));

        String endpoint = "/api/v1/accounts/" + account.getNumber() + "/transactions";
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        expectedResultSize(transactionList.size(), result);
    }

    @Test
    @DisplayName("Should no transactions by account number when empty")
    void getAccountsTransactionsByAccountNumberWhenEmpty() {
        Account account = accountRepository.saveAndFlush(AccountGenerator.getAccount());

        String endpoint = "/api/v1/accounts/" + account.getNumber() + "/transactions";
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        expectedResultSize(0, result);
    }

    @Test
    @DisplayName("Should get all accounts")
    void getAllAccounts() {
        List<Account> accountList = List.of(
                AccountGenerator.getAccount(),
                AccountGenerator.getAccount(),
                AccountGenerator.getAccount()
        );
        accountList.forEach(account -> accountRepository.saveAndFlush(account));

        String endpoint = "/api/v1/accounts";
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        expectedResultSize(accountList.size(), result);
    }

    @Test
    @DisplayName("Should get no accounts")
    void getAllAccountsWhenEmpty() {
        String endpoint = "/api/v1/accounts";
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        expectedResultSize(0, result);
    }

    @Test
    @DisplayName("Should create an Account")
    void postCreateAccount() throws UnsupportedEncodingException {
        CreateAccountDTO createAccountDTO = CreateAccountDTOGenerator.getCreateAccountDTO();

        String endpoint = "/api/v1/accounts";
        MvcResult result = performPost(mockMvc, endpoint, createAccountDTO, HttpStatus.CREATED).andReturn();

        String response = result.getResponse().getContentAsString();

        Assertions.assertNotNull(accountRepository.findByNumber(
                UUID.fromString(JsonPath.read(response, "$.number"))));
    }
}
