package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.MotherCreator;

public final class TransactionDescriptionMother {

    public static TransactionDescription create(String value) {
        return new TransactionDescription(value);
    }

    public static TransactionDescription random() {
        return create(MotherCreator.random().lorem().sentence(3));
    }
}
