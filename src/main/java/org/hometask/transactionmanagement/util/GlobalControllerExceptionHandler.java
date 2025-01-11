package org.hometask.transactionmanagement.util;

import org.hometask.transactionmanagement.exceptions.DuplicateTransactionException;
import org.hometask.transactionmanagement.exceptions.NonExistTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static java.lang.System.err;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(DuplicateTransactionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleDuplicateTransaction(DuplicateTransactionException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NonExistTransactionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNonExistTransaction(NonExistTransactionException ex) {
        return ex.getMessage();
    }
}
