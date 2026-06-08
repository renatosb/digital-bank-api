package com.digital.bank.api.integration.transaction;

import com.digital.bank.api.dto.AccountDTO;
import com.digital.bank.api.dto.PerformTransactionDTO;
import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import com.digital.bank.api.exception.account.AccountNotFoundException;
import com.digital.bank.api.exception.transaction.TransactionNotFoundException;
import com.digital.bank.api.generator.AccountGenerator;
import com.digital.bank.api.generator.TransactionGenerator;
import com.digital.bank.api.generator.dto.PerformTransactionDTOGenerator;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest extends BaseControllerTest {

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
    @DisplayName("Should get all transactions")
    void getAllTransactions() {
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

        String endpoint = "/api/v1/transactions";
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        expectedResultSize(transactionList.size(), result);
    }

    @Test
    @DisplayName("Should get no transactions")
    void getAllAccountsWhenEmpty() {
        String endpoint = "/api/v1/transactions";
        ResultActions result = performGet(mockMvc, endpoint, HttpStatus.OK);

        expectedResultSize(0, result);
    }

    @Test
    @DisplayName("Should perform a transaction")
    void PerformTransaction() throws Exception {
        Account sourceAccount = AccountGenerator.getAccount();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount = accountRepository.saveAndFlush(sourceAccount);

        Account destinationAccount = AccountGenerator.getAccount();
        destinationAccount.setAmount(BigDecimal.TEN);
        destinationAccount = accountRepository.saveAndFlush(destinationAccount);

        PerformTransactionDTO performTransactionDTO = new PerformTransactionDTO(
                sourceAccount.getNumber(),
                destinationAccount.getNumber(),
                BigDecimal.ONE
        );

        String endpoint = "/api/v1/transactions";
        MvcResult result = performPost(mockMvc, endpoint, performTransactionDTO, HttpStatus.OK).andReturn();
        String response = result.getResponse().getContentAsString();

        Integer transactionId = JsonPath.read(response, "$.id");

        Transaction transaction = transactionRepository.findById( transactionId.longValue())
                .orElseThrow(TransactionNotFoundException::new);

        Assertions.assertEquals(transaction.getSourceAccount().getClient(),
                JsonPath.read(response, "$.sourceAccount.client")
        );
        Assertions.assertEquals(transaction.getDestinationAccount().getClient(),
                JsonPath.read(response, "$.destinationAccount.client")
        );

        Integer amount = JsonPath.read(response, "$.amount");
        Assertions.assertEquals(transaction.getAmount().intValue(), amount);
    }
}
