package com.digital.bank.api.integration.transaction;

import com.digital.bank.api.integration.BaseControllerTest;
import com.digital.bank.api.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setup(){
        transactionRepository.deleteAll();
        transactionRepository.flush();
    }
}
