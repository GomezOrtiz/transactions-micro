package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gomezortiz.transactionsmicro.shared.serialization.CustomOffsetDateTimeDeserializer;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class TransactionCreateRequest {

    @JsonProperty("reference")
    private final String reference;

    @JsonProperty("account_iban")
    private final String accountIban;

    @JsonProperty("date")
    @JsonDeserialize(using = CustomOffsetDateTimeDeserializer.class)
    private final OffsetDateTime date;

    @JsonProperty("amount")
    private final Double amount;

    @JsonProperty("fee")
    private final Double fee;

    @JsonProperty("description")
    private final String description;

    public final Transaction toPendingTransaction()  {
        return new Transaction(
                new TransactionReference(null != reference ? reference : UUID.randomUUID().toString()),
                new TransactionIban(accountIban),
                new TransactionDate(date),
                new TransactionAmount(amount),
                new TransactionFee(fee),
                new TransactionDescription(description)
        );
    }
}
