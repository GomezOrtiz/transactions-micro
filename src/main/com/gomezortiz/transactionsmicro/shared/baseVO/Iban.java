package com.gomezortiz.transactionsmicro.shared.baseVO;

import com.gomezortiz.transactionsmicro.transactions.domain.exception.IbanNotValidException;
import lombok.*;
import lombok.experimental.Accessors;
import nl.garvelink.iban.Modulo97;

import java.io.Serializable;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class Iban implements Serializable {

    protected final String value;

    public Iban(String value) {
        validate(value);
        this.value = value;
    }

    protected Iban() {
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

    private static final long serialVersionUID = -7319794667186404548L;
}
