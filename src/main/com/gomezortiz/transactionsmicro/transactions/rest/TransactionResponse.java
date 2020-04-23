package com.gomezortiz.transactionsmicro.transactions.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gomezortiz.transactionsmicro.shared.serialization.CustomOffsetDateTimeSerializer;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class TransactionResponse {

    @JsonProperty("reference")
    private final String reference;

    @JsonProperty("account_iban")
    private final String accountIban;

    @JsonProperty("date")
    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    private final OffsetDateTime date;

    @JsonProperty("amount")
    private final Double amount;

    @JsonProperty("fee")
    private final Double fee;

    @JsonProperty("description")
    private final String description;

    public static TransactionResponse fromTransaction(Transaction transaction) {
        return new TransactionResponse(
                transaction.reference().value(),
                transaction.accountIban().value(),
                transaction.date().value(),
                transaction.amount().value(),
                transaction.fee().value(),
                transaction.description().value()
        );
    }
}
