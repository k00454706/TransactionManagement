package org.hometask.transactionmanagement.exceptions;

public class DuplicateTransactionException extends RuntimeException {
    // 无参构造函数，虽然在hometask里没有用到，但考虑代码健壮性，保留。
    public DuplicateTransactionException() {
        super();
    }

    // 带错误信息的构造函数
    public DuplicateTransactionException(String message) {
        super(message);
    }

    // 带错误信息和原因的构造函数
    public DuplicateTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    // 带原因的构造函数
    public DuplicateTransactionException(Throwable cause) {
        super(cause);
    }
}
