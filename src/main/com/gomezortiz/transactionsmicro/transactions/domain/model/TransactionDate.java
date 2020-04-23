package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.baseVO.OffsetDateTimeVO;

import java.time.OffsetDateTime;

public final class TransactionDate extends OffsetDateTimeVO {
    public TransactionDate(OffsetDateTime value) {
        super(value);
    }
}
