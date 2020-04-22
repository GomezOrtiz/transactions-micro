package com.gomezortiz.transactionsmicro.accounts.domain.model;

public final class AccountMother {

    public static Account create(AccountIban iban, AccountBalance balance) {
        return new Account(iban, balance);
    }

    public static Account random(String countryCode) {
        return create(
                AccountIbanMother.random(countryCode), AccountBalanceMother.random(0, 1000)
        );
    }

    public static Account withBalance(String countryCode, Double balance) {
        return create(
                AccountIbanMother.random(countryCode), AccountBalanceMother.create(balance)
        );
    }
}
