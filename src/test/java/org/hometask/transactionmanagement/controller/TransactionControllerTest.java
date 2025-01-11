package org.hometask.transactionmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.service.TransactionService;
import org.hometask.transactionmanagement.util.GlobalControllerExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@Transactional 如果是在真实数据库中操作，需要使用该注解，确保测试后数据回滚
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private GlobalControllerExceptionHandler globalControllerExceptionHandler;

    private ObjectMapper objectMapper;
    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();

        // 添加Java日期时间模块支持
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 创建测试用的Transaction对象
        testTransaction = new Transaction();
        testTransaction.setTransactionNumber("No.1");
    }

    @Test
    void createTransaction_ShouldCreateAndReturnTransaction() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Transaction responseTransaction = objectMapper.readValue(responseContent, Transaction.class);

        assertNotNull(responseTransaction, "Response transaction should not be null");
        assertNotNull(responseTransaction.getId(), "Transaction ID should not be null");
        assertEquals(testTransaction.getTransactionNumber(), responseTransaction.getTransactionNumber(), "Transaction transactionNumber should match");

        // 验证内存中确实保存了该记录
        List<Transaction> transactions = transactionService.listAllTransactions();
        assertEquals(testTransaction.getTransactionNumber(), transactions.get(0).getTransactionNumber());

        // 删除数据，避免对其他case的影响
        transactionService.deleteTransaction(responseTransaction.getId());
    }

    @Test
    void createTransaction_WhenDuplicate_ShouldThrowDuplicateTransactionException() throws Exception {
        testTransaction.setId(1l);
        mockMvc.perform(post("/api/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isConflict());

        // 删除数据，避免对其他case的影响
        transactionService.deleteTransaction(testTransaction.getId());
    }

    @Test
    void deleteTransaction_WhenNegative_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/transaction/" + -1l))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTransaction_WhenNotExist_ShouldThrowNonExistTransactionException() throws Exception {
        mockMvc.perform(delete("/api/v1/transaction/" + 10l))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTransaction_ShouldDeleteSuccessfully() throws Exception {
        Transaction savedTransaction = transactionService.createTransaction(testTransaction);
        assertNotNull(savedTransaction.getId(), "Transaction should be saved first");

        mockMvc.perform(delete("/api/v1/transaction/" + savedTransaction.getId()))
                .andExpect(status().isOk());

        // 验证记录已被删除
        assertEquals(0, transactionService.listAllTransactions().size());
    }


    @Test
    void modifyTransaction_WhenNotExist_ShouldThrowNonExistTransactionException() throws Exception {
        mockMvc.perform(put("/api/v1/transaction/" + 11)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isNotFound());
    }

    @Test
    void modifyTransaction_ShouldUpdateAndReturnTransaction() throws Exception {
        // 先创建一个交易
        Transaction savedTransaction = transactionService.createTransaction(testTransaction);

        // 修改交易编号
        savedTransaction.setTransactionNumber("No.2");

        MvcResult result = mockMvc.perform(put("/api/v1/transaction/" + savedTransaction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedTransaction)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Transaction responseTransaction = objectMapper.readValue(responseContent, Transaction.class);

        assertNotNull(responseTransaction, "Response transaction should not be null");
        assertEquals(savedTransaction.getId(), responseTransaction.getId(), "Transaction ID should match");
        assertEquals("No.2", responseTransaction.getTransactionNumber(), "Transaction transactionNumber should be updated");

        // 验证内存中的记录已更新
        List<Transaction> transactions = transactionService.listAllTransactions();
        assertEquals("No.2", transactions.get(0).getTransactionNumber(), "Transaction transactionNumber should be updated in database");

        // 删除数据，避免对其他case的影响
        transactionService.deleteTransaction(transactions.get(0).getId());
    }

    @Test
    void listAllTransactions_ShouldReturnAllTransactions() throws Exception {
        // 先创建2个交易
        transactionService.createTransaction(testTransaction);

        Transaction anotherTransaction = new Transaction();
        anotherTransaction.setTransactionNumber("No.2");
        transactionService.createTransaction(anotherTransaction);

        MvcResult result = mockMvc.perform(get("/api/v1/transaction/list"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Transaction> responseTransactions = objectMapper.readValue(
                responseContent,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class)
        );

        assertNotNull(responseTransactions, "Response transactions list should not be null");
        assertFalse(responseTransactions.isEmpty(), "Response transactions list should not be empty");
        assertTrue(responseTransactions.size() >= 2, "Should return at least two transactions");

        // 验证返回的交易列表中包含我们创建的交易
        boolean foundTestTransaction = responseTransactions.stream()
                .anyMatch(t -> t.getTransactionNumber().equals("No.1"));
        boolean foundAnotherTransaction = responseTransactions.stream()
                .anyMatch(t -> t.getTransactionNumber().equals("No.2"));

        assertTrue(foundTestTransaction, "Should find transaction with transactionNumber No.1");
        assertTrue(foundAnotherTransaction, "Should find transaction with transactionNumber No.2");

        // 删除数据，避免对其他case的影响
        transactionService.deleteTransaction(responseTransactions.get(0).getId());
        transactionService.deleteTransaction(responseTransactions.get(1).getId());
    }
}