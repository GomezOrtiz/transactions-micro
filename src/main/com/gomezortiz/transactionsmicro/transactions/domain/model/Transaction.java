package com.gomezortiz.transactionsmicro.transactions.domain.model;

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
public final class Transaction {

    private final TransactionReference reference;
    private final TransactionStatus status;
    private final TransactionIban accountIban;
    private final TransactionDate date;
    private final TransactionAmount amount;
    private final TransactionFee fee;
    private final TransactionDescription description;
}
