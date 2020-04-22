package com.gomezortiz.transactionsmicro.shared.domain.model.baseVO;

import com.gomezortiz.transactionsmicro.transactions.domain.exception.IdNotValidException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class Identifier implements Serializable {

    protected final String value;

    public Identifier(String value) {
        validate(value);
        this.value = value;
    }

    protected Identifier() {
        this.value = null;
    }

    private void validate(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IdNotValidException(id, e);
        }
    }
}

