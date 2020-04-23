package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.DoubleMother;

public final class TransactionAmountMother {

    public static TransactionAmount create(Double value) {
        return new TransactionAmount(value);
    }

    public static TransactionAmount random(int min, int max) {
        return create(DoubleMother.random(2, min, max));
    }
}
