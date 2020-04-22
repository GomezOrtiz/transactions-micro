package com.gomezortiz.transactionsmicro.accounts.application.find;

public final class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String iban) {
        super(String.format("No account with IBAN %s", iban));
    }
}
