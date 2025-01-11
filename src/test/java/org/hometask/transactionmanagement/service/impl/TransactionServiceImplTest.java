package org.hometask.transactionmanagement.service.impl;

import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.exceptions.DuplicateTransactionException;
import org.hometask.transactionmanagement.exceptions.NonExistTransactionException;
import org.hometask.transactionmanagement.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setTransactionNumber("No.1");
        testTransaction.setCreateTime(LocalDateTime.now());
        testTransaction.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void createTransaction_Success() {
        when(repository.findById(any())).thenReturn(null);
        when(repository.save(any(Transaction.class))).thenReturn(testTransaction);

        Transaction result = transactionService.createTransaction(testTransaction);

        assertNotNull(result);
        assertEquals(testTransaction.getTransactionNumber(), result.getTransactionNumber());
        assertNotNull(result.getCreateTime());
        assertNotNull(result.getUpdateTime());
        verify(repository).save(any(Transaction.class));
    }

    @Test
    void createTransaction_WhenDuplicate_ShouldThrowException() {
        when(repository.findById(testTransaction.getId())).thenReturn(testTransaction);

        assertThrows(DuplicateTransactionException.class, () -> {
            transactionService.createTransaction(testTransaction);
        });
        verify(repository, never()).save(any(Transaction.class));
    }

    @Test
    void createTransaction_WithoutId_ShouldSucceed() {
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionNumber("No.2");
        when(repository.save(any(Transaction.class))).thenReturn(newTransaction);

        Transaction result = transactionService.createTransaction(newTransaction);

        assertNotNull(result);
        assertEquals(newTransaction.getTransactionNumber(), result.getTransactionNumber());
        assertNotNull(result.getCreateTime());
        assertNotNull(result.getUpdateTime());
        verify(repository).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction_Success() {
        when(repository.findById(1L)).thenReturn(testTransaction);
        doNothing().when(repository).delete(1L);

        transactionService.deleteTransaction(1L);

        verify(repository).delete(1L);
    }

    @Test
    void deleteTransaction_WhenNotExist_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(null);

        assertThrows(NonExistTransactionException.class, () -> {
            transactionService.deleteTransaction(1L);
        });
        verify(repository, never()).delete(anyLong());
    }

    @Test
    void modifyTransaction_Success() {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setId(1L);
        updatedTransaction.setTransactionNumber("No.1-Updated");

        when(repository.findById(1L)).thenReturn(testTransaction);
        when(repository.modify(any(Transaction.class))).thenReturn(updatedTransaction);

        Transaction result = transactionService.modifyTransaction(1L, updatedTransaction);

        assertNotNull(result);
        assertEquals("No.1-Updated", result.getTransactionNumber());
        assertNotNull(result.getUpdateTime());
        verify(repository).modify(any(Transaction.class));
    }

    @Test
    void modifyTransaction_WhenNotExist_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(null);

        assertThrows(NonExistTransactionException.class, () -> {
            transactionService.modifyTransaction(1L, testTransaction);
        });
        verify(repository, never()).modify(any(Transaction.class));
    }

    @Test
    void listAllTransactions_Success() {
        Transaction anotherTransaction = new Transaction();
        anotherTransaction.setId(2L);
        anotherTransaction.setTransactionNumber("No.2");

        List<Transaction> transactions = Arrays.asList(testTransaction, anotherTransaction);
        when(repository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.listAllTransactions();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("No.1", result.get(0).getTransactionNumber());
        assertEquals("No.2", result.get(1).getTransactionNumber());
        verify(repository).findAll();
    }

    @Test
    void listAllTransactions_WhenEmpty_ShouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(Arrays.asList());

        List<Transaction> result = transactionService.listAllTransactions();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }
}