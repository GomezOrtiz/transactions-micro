package com.gomezortiz.transactionsmicro.transactions.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class IbanNotValidException extends RuntimeException {

    public IbanNotValidException(String iban) {
        super(String.format("%s is not a valid IBAN code", iban));
    }

    public IbanNotValidException(String iban, Throwable cause) {
        super(String.format("%s is not a valid IBAN code", iban), cause);
    }
}
