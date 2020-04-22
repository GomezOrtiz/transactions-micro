package com.gomezortiz.transactionsmicro.accounts.domain.model;

import com.gomezortiz.transactionsmicro.transactions.domain.exception.IbanNotValidException;
import lombok.*;
import lombok.experimental.Accessors;
import nl.garvelink.iban.Modulo97;

import java.io.Serializable;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public abstract class AccountIban implements Serializable {

    protected final String value;

    public AccountIban(String value) {
        validate(value);
        this.value = value;
    }

    protected AccountIban() {
        this.value = null;
    }

    private final void validate(String iban) {
        try {
            if(!Modulo97.verifyCheckDigits(iban)) {
                throw new IbanNotValidException(iban);
            }
        } catch (IllegalArgumentException e) {
            throw new IbanNotValidException(iban, e);
        }
    }
}
