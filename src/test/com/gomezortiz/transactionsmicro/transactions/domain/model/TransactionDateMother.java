package com.gomezortiz.transactionsmicro.transactions.domain.model;

import java.time.OffsetDateTime;

public final class TransactionDateMother {

    public static TransactionDate create(OffsetDateTime value) {
        return new TransactionDate(value);
    }

    public static TransactionDate now() {
        return create(OffsetDateTime.now());
    }
    public static TransactionDate yesterday() {
        return create(OffsetDateTime.now().minusDays(1));
    }
    public static TransactionDate tomorrow() {
        return create(OffsetDateTime.now().plusDays(1));
    }
}
