package com.gomezortiz.transactionsmicro.transactions.application.create;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class TransactionNotValidException extends RuntimeException {

    public TransactionNotValidException(Double accountBalance, Double amount) {
        super(String.format("Transaction is not valid. Account balance %s is not enough to transfer %s", accountBalance, amount));
    }
}
