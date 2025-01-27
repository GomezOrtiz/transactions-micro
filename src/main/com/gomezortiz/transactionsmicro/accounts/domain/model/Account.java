package com.gomezortiz.transactionsmicro.accounts.domain.model;

import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
public final class Account {

    private final AccountIban iban;
    private final AccountBalance balance;
}
