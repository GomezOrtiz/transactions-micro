package com.gomezortiz.transactionsmicro.transactions.domain.model;

public final class TransactionMother {

    public static Transaction create(
            TransactionReference reference, TransactionStatus status,
            TransactionIban accountIban, TransactionDate date,
            TransactionAmount amount, TransactionFee fee,
            TransactionDescription description
    ) {
        return new Transaction(reference, status, accountIban, date, amount, fee, description);
    }

    public static Transaction randomForSpain() {
        return create(
                TransactionReferenceMother.random(), TransactionStatusMother.random(),
                TransactionAccountIbanMother.random("ES"), TransactionDateMother.now(),
                TransactionAmountMother.random(10,1000), TransactionFeeMother.create(3.18),
                TransactionDescriptionMother.random()
        );
    }
}
