package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.IdMother;

public final class TransactionReferenceMother {

    public static TransactionReference create(String value) {
        return new TransactionReference(value);
    }

    public static TransactionReference random() {
        return create(IdMother.random());
    }
}
