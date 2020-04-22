package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.DoubleMother;

public final class TransactionFeeMother {

    public static TransactionFee create(Double value) {
        return new TransactionFee(value);
    }

    public static TransactionFee random(int min, int max) {
        return create(DoubleMother.random(2, min, max));
    }
}
