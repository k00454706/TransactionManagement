package org.hometask.transactionmanagement.controller;

import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.exceptions.DuplicateTransactionException;
import org.hometask.transactionmanagement.exceptions.NonExistTransactionException;
import org.hometask.transactionmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction transaction1 = transactionService.createTransaction(transaction);
            return ResponseEntity.ok(transaction1);
        } catch (DuplicateTransactionException ex) {
            // 异常的捕获处理，可以在全局异常处理类中，统一处理
            // 并记录日志
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        try {
            // 参数校验，亦可通过注解实现
            if (id == null || id <= 0) {
                // 情况可以细分
                // id == null 提示"Trasaction ID cannot be null"
                // id <= 0  提示"Transaction ID must be a positive number"
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            transactionService.deleteTransaction(id);
            return ResponseEntity.ok().build();
        } catch (NonExistTransactionException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> modifyTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {
        try {
            // 参数校验，亦可通过注解实现
            if (id == null || id <= 0) {
                // 情况可以细分
                // id == null 提示"Trasaction ID cannot be null"
                // id <= 0  提示"Transaction ID must be a positive number"
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Transaction transaction1 = transactionService.modifyTransaction(id, transaction);
            return ResponseEntity.ok(transaction1);
        } catch (NonExistTransactionException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Transaction>> listAllTransactions() {
        List<Transaction> transactions = transactionService.listAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}