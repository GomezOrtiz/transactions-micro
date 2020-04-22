package com.gomezortiz.transactionsmicro.transactions.domain.exception;

public final class IdNotValidException extends RuntimeException {

    public IdNotValidException(String id) {
        super(String.format("%s is not a valid identifier", id));
    }

    public IdNotValidException(String id, Throwable cause) {
        super(String.format("%s is not a valid identifier", id), cause);
    }
}
