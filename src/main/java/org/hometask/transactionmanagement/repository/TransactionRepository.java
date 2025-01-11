package org.hometask.transactionmanagement.repository;

import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.exceptions.DuplicateTransactionException;
import org.hometask.transactionmanagement.exceptions.NonExistTransactionException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.System.err;

@Repository
public class TransactionRepository {
    // 使用ConcurrentHashMap来保证线程安全
    private final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();
    // 用于生成自增ID
    private final AtomicLong idGenerator = new AtomicLong(1);

    // 创建交易
    public Transaction save(Transaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(idGenerator.getAndIncrement());
        }
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    public Transaction modify(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    // 根据ID查询交易
    public Transaction findById(Long id) {
        return transactions.get(id);
    }

    // 查询所有交易
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    // 删除交易
    public void delete(Long id) {
        if (!transactions.containsKey(id)) {
            String message = "Transaction: " + id + " dose not exists.";
            throw new NonExistTransactionException(message);
        }
        transactions.remove(id);
    }
} 