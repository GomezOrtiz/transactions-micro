package com.gomezortiz.transactionsmicro.accounts.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.DoubleMother;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionAmount;

public final class AccountBalanceMother {

    public static AccountBalance create(Double value) {
        return new AccountBalance(value);
    }

    public static AccountBalance random(int min, int max) {
        return create(DoubleMother.random(2, min, max));
    }
}
