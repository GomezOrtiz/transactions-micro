package com.gomezortiz.transactionsmicro.accounts.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.IbanMother;

public final class AccountIbanMother {

    public static AccountIban create(String value) {
        return new AccountIban(value);
    }

    public static AccountIban random(String countryCode) {
        return create(IbanMother.random(countryCode));
    }
}
