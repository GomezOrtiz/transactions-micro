package com.gomezortiz.transactionsmicro.transactions.application.status;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class TransactionStatusResponse {

    private final String reference;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final TransactionStatus status;

    private final Double amount;

    private final Double fee;
}
