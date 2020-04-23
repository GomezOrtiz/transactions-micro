package com.gomezortiz.transactionsmicro.accounts.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.DoubleMother;

public final class AccountBalanceMother {

    public static AccountBalance create(Double value) {
        return new AccountBalance(value);
    }

    public static AccountBalance random(int min, int max) {
        return create(DoubleMother.random(2, min, max));
    }
}
