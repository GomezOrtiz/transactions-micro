package com.gomezortiz.transactionsmicro.transactions.application.status;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class TransactionStatusResponse {

    @JsonProperty("reference")
    private final String reference;

    @JsonProperty("status")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final TransactionStatus status;

    @JsonProperty("amount")
    private final Double amount;

    @JsonProperty("fee")
    private final Double fee;
}
