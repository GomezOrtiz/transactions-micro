package com.gomezortiz.transactionsmicro.transactions.domain.exception;

public final class TransactionNotValidException extends RuntimeException {

    public TransactionNotValidException(Double accountBalance, Double amount) {
        super(String.format("Transaction is not valid. Account balance %s is not enough to transfer %s", accountBalance, amount));
    }
}
