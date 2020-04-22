package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.IbanMother;

public final class TransactionAccountIbanMother {

    public static TransactionIban create(String value) {
        return new TransactionIban(value);
    }

    public static TransactionIban random(String countryCode) {
        return create(IbanMother.random(countryCode));
    }
}
