package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.gomezortiz.transactionsmicro.shared.domain.model.DoubleMother;
import com.gomezortiz.transactionsmicro.shared.domain.model.IbanMother;
import com.gomezortiz.transactionsmicro.shared.domain.model.IdMother;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;

import java.time.LocalDate;

public final class TransactionCreateRequestMother {

    public static TransactionCreateRequest create(
            String reference, String accountIban, LocalDate date,
            Double amount, Double fee, String description
    ) {
        return new TransactionCreateRequest(reference, accountIban, date, amount, fee, description);
    }

    public static TransactionCreateRequest random(boolean withId, boolean isDebit, String countryCode) {
        return create(
                withId ? IdMother.random() : null, IbanMother.random(countryCode),
                LocalDate.now(),
                isDebit ? DoubleMother.random(2, -300,-1) : DoubleMother.random(2, 1,300),
                3.18,
                TransactionDescriptionMother.random().value()
        );
    }
}
