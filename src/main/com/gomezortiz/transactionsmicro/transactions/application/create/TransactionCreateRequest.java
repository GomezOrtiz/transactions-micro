package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gomezortiz.transactionsmicro.shared.domain.model.serialization.CustomLocalDateDeserializer;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class TransactionCreateRequest {

    private final String reference;

    @JsonProperty("account_iban")
    private final String accountIban;

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private final LocalDate date;

    private final Double amount;

    private final Double fee;

    private final String description;

    public final Transaction toPendingTransaction()  {
        return new Transaction(
                new TransactionReference(reference),
                TransactionStatus.PENDING,
                new TransactionAccountIban(accountIban),
                new TransactionDate(date),
                new TransactionAmount(amount),
                new TransactionFee(fee),
                new TransactionDescription(description)
        );
    }
}
