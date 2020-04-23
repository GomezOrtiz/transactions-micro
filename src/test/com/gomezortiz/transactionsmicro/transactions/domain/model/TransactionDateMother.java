package com.gomezortiz.transactionsmicro.transactions.domain.model;

import java.time.LocalDate;

public final class TransactionDateMother {

    public static TransactionDate create(LocalDate value) {
        return new TransactionDate(value);
    }

    public static TransactionDate now() {
        return create(LocalDate.now());
    }
    public static TransactionDate yesterday() {
        return create(LocalDate.now().minusDays(1));
    }
    public static TransactionDate tomorrow() {
        return create(LocalDate.now().plusDays(1));
    }
}
