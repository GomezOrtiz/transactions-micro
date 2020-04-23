package com.gomezortiz.transactionsmicro.transactions.application.status;

import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class TransactionStatusResponse {

    private final String reference;
    private final TransactionStatus status;
    private final Double amount;
    private final Double fee;
}
