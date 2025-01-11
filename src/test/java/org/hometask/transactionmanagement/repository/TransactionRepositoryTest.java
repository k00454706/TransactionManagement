package org.hometask.transactionmanagement.repository;

import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.exceptions.NonExistTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private TransactionRepository repository;
    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        repository = new TransactionRepository();

        testTransaction = new Transaction();
        testTransaction.setTransactionNumber("No.1");
        testTransaction.setAmount(BigDecimal.valueOf(100.0));
        testTransaction.setCreateTime(LocalDateTime.now());
        testTransaction.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void save_WithoutId_ShouldGenerateIdAndSave() {
        Transaction savedTransaction = repository.save(testTransaction);

        assertNotNull(savedTransaction);
        assertNotNull(savedTransaction.getId());
        assertEquals(testTransaction.getTransactionNumber(), savedTransaction.getTransactionNumber());

        // 校验是否保存
        Transaction foundTransaction = repository.findById(savedTransaction.getId());
        assertNotNull(foundTransaction);
        assertEquals(savedTransaction.getId(), foundTransaction.getId());
    }

    @Test
    void save_WithId_ShouldSaveWithProvidedId() {
        testTransaction.setId(100L);

        Transaction savedTransaction = repository.save(testTransaction);

        assertNotNull(savedTransaction);
        assertEquals(100L, savedTransaction.getId());

        // 校验是否保存
        Transaction foundTransaction = repository.findById(100L);
        assertNotNull(foundTransaction);
        assertEquals(100L, foundTransaction.getId());
    }

    @Test
    void save_MultipleTimes_ShouldGenerateSequentialIds() {
        Transaction firstTransaction = repository.save(new Transaction());
        Transaction secondTransaction = repository.save(new Transaction());
        Transaction thirdTransaction = repository.save(new Transaction());

        assertEquals(1L, firstTransaction.getId());
        assertEquals(2L, secondTransaction.getId());
        assertEquals(3L, thirdTransaction.getId());
    }

    @Test
    void modify_ExistingTransaction_ShouldUpdate() {
        Transaction savedTransaction = repository.save(testTransaction);
        savedTransaction.setTransactionNumber("No.1-Updated");
        savedTransaction.setAmount(BigDecimal.valueOf(200.0));

        Transaction modifiedTransaction = repository.modify(savedTransaction);

        assertNotNull(modifiedTransaction);
        assertEquals("No.1-Updated", modifiedTransaction.getTransactionNumber());
        assertEquals(BigDecimal.valueOf(200.0), modifiedTransaction.getAmount());

        // 校验是否更新
        Transaction foundTransaction = repository.findById(savedTransaction.getId());
        assertEquals("No.1-Updated", foundTransaction.getTransactionNumber());
        assertEquals(BigDecimal.valueOf(200.0), foundTransaction.getAmount());
    }

    @Test
    void findById_ExistingTransaction_ShouldReturn() {
        Transaction savedTransaction = repository.save(testTransaction);

        Transaction foundTransaction = repository.findById(savedTransaction.getId());

        assertNotNull(foundTransaction);
        assertEquals(savedTransaction.getId(), foundTransaction.getId());
        assertEquals(testTransaction.getTransactionNumber(), foundTransaction.getTransactionNumber());
    }

    @Test
    void findById_NonExistingTransaction_ShouldReturnNull() {
        Transaction foundTransaction = repository.findById(999L);

        assertNull(foundTransaction);
    }

    @Test
    void findAll_WithMultipleTransactions_ShouldReturnAll() {
        Transaction firstTransaction = repository.save(testTransaction);

        Transaction secondTransaction = new Transaction();
        secondTransaction.setTransactionNumber("No.2");
        repository.save(secondTransaction);

        List<Transaction> allTransactions = repository.findAll();

        assertNotNull(allTransactions);
        assertEquals(2, allTransactions.size());
        assertTrue(allTransactions.stream()
                .anyMatch(t -> t.getTransactionNumber().equals("No.1")));
        assertTrue(allTransactions.stream()
                .anyMatch(t -> t.getTransactionNumber().equals("No.2")));
    }

    @Test
    void findAll_WithNoTransactions_ShouldReturnEmptyList() {
        List<Transaction> allTransactions = repository.findAll();

        assertNotNull(allTransactions);
        assertTrue(allTransactions.isEmpty());
    }

    @Test
    void delete_ExistingTransaction_ShouldRemove() {
        Transaction savedTransaction = repository.save(testTransaction);

        repository.delete(savedTransaction.getId());

        Transaction foundTransaction = repository.findById(savedTransaction.getId());
        assertNull(foundTransaction);

        List<Transaction> allTransactions = repository.findAll();
        assertTrue(allTransactions.isEmpty());
    }

    @Test
    void delete_NonExistingTransaction_ShouldThrowException() {
        assertThrows(NonExistTransactionException.class, () -> {
            repository.delete(999L);
        });
    }

    @Test
    void concurrentOperations_ShouldHandleCorrectly() throws InterruptedException {
        int numberOfThreads = 10;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                Transaction transaction = new Transaction();
                transaction.setTransactionNumber("No." + index);
                repository.save(transaction);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        List<Transaction> allTransactions = repository.findAll();
        assertEquals(numberOfThreads, allTransactions.size());
        assertEquals(numberOfThreads, allTransactions.stream()
                .map(Transaction::getId)
                .distinct()
                .count());
    }
}