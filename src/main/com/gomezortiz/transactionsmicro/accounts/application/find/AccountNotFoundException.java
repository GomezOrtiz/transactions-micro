package com.gomezortiz.transactionsmicro.accounts.application.find;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String iban) {
        super(String.format("No account with IBAN %s", iban));
    }
}
