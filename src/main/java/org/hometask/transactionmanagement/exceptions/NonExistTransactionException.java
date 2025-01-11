package org.hometask.transactionmanagement.exceptions;

public class NonExistTransactionException extends RuntimeException {

    public NonExistTransactionException() {
        super();
    }
    
    public NonExistTransactionException(String message) {
        super(message);
    }

    public NonExistTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistTransactionException(Throwable cause) {
        super(cause);
    }
}
