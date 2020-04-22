package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.IbanMother;

public final class TransactionAccountIbanMother {

    public static TransactionAccountIban create(String value) {
        return new TransactionAccountIban(value);
    }

    public static TransactionAccountIban random(String countryCode) {
        return create(IbanMother.random(countryCode));
    }
}
