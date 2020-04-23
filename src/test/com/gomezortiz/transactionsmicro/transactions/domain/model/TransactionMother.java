package com.gomezortiz.transactionsmicro.transactions.domain.model;

public final class TransactionMother {

    public static Transaction create(
            TransactionReference reference,
            TransactionIban accountIban, TransactionDate date,
            TransactionAmount amount, TransactionFee fee,
            TransactionDescription description
    ) {
        return new Transaction(reference, accountIban, date, amount, fee, description);
    }

    public static Transaction withIbanAndAmount(TransactionIban iban, TransactionAmount amount) {
        return create(
                TransactionReferenceMother.random(),
                iban, TransactionDateMother.now(),
                amount, TransactionFeeMother.create(3.18),
                TransactionDescriptionMother.random()
        );
    }

    public static Transaction randomForSpain() {
        return create(
                TransactionReferenceMother.random(),
                TransactionAccountIbanMother.random("ES"), TransactionDateMother.now(),
                TransactionAmountMother.random(10,1000), TransactionFeeMother.create(3.18),
                TransactionDescriptionMother.random()
        );
    }

    public static Transaction beforeToday() {
        return create(
                TransactionReferenceMother.random(),
                TransactionAccountIbanMother.random("ES"), TransactionDateMother.yesterday(),
                TransactionAmountMother.random(10,1000), TransactionFeeMother.create(3.18),
                TransactionDescriptionMother.random()
        );
    }

    public static Transaction afterToday() {
        return create(
                TransactionReferenceMother.random(),
                TransactionAccountIbanMother.random("ES"), TransactionDateMother.tomorrow(),
                TransactionAmountMother.random(10,1000), TransactionFeeMother.create(3.18),
                TransactionDescriptionMother.random()
        );
    }
}
