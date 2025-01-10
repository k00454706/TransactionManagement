package org.hometask.transactionmanagement.controller;

import org.hometask.transactionmanagement.entity.Transaction;
import org.hometask.transactionmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(transaction1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> modifyTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.modifyTransaction(id, transaction);
        return ResponseEntity.ok(transaction1);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Transaction>> listAllTransactions() {
        List<Transaction> transactions = transactionService.listAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
