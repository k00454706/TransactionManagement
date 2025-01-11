package org.hometask.transactionmanagement.service.impl;

import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.exceptions.DuplicateTransactionException;
import org.hometask.transactionmanagement.exceptions.NonExistTransactionException;
import org.hometask.transactionmanagement.repository.TransactionRepository;
import org.hometask.transactionmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getId() != null) {
            Long transactionId = transaction.getId();
            if (repository.findById(transactionId) != null) {
                throw new DuplicateTransactionException("Transaction already exists");
            }
        }
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setUpdateTime(LocalDateTime.now());
        return repository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction existingTransaction = repository.findById(id);
        if (existingTransaction == null) {
            throw new NonExistTransactionException("Transaction not found");
        }
        repository.delete(id);
    }

    @Override
    public Transaction modifyTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = repository.findById(id);
        if (existingTransaction == null) {
            throw new NonExistTransactionException("Transaction not found");
        }
        transaction.setUpdateTime(LocalDateTime.now());
        return repository.modify(transaction);
    }

    @Override
    public List<Transaction> listAllTransactions() {
        return repository.findAll();
    }
}
