package com.gomezortiz.transactionsmicro.transactions.domain.model;

import org.apache.commons.lang3.RandomUtils;

public final class TransactionStatusMother {

    public static TransactionStatus random() {
        return TransactionStatus.values()[RandomUtils.nextInt(0, TransactionStatus.values().length)];
    }
}
