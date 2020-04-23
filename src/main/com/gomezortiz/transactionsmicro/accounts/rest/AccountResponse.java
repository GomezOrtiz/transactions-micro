package com.gomezortiz.transactionsmicro.accounts.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class AccountResponse {

    @JsonProperty("account_iban")
    private final String accountIban;

    @JsonProperty("balance")
    private final Double balance;

    public static AccountResponse fromAccount(Account account) {
        return new AccountResponse(
                account.iban().value(),
                account.balance().value()
        );
    }
}
