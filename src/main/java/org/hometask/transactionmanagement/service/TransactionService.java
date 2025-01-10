package org.hometask.transactionmanagement.service;

import org.hometask.transactionmanagement.entity.Transaction;

import java.util.List;

public interface TransactionService {
    /**
     * 创建交易记录
     * @param request 交易请求对象
     * @return 交易响应对象
     */
    Transaction createTransaction(Transaction request);

    /**
     * 删除交易记录
     * @param id 交易ID
     */
    void deleteTransaction(Long id);

    /**
     * 修改交易记录
     * @param id 交易ID
     * @param request 交易请求对象
     * @return 交易响应对象
     */
    Transaction modifyTransaction(Long id, Transaction request);

    /**
     * 获取所有交易记录
     * @return 交易记录列表
     */
    List<Transaction> listAllTransactions();
}
