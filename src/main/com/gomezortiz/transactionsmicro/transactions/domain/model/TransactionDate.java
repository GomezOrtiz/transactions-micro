package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.baseVO.LocalDateVO;

import java.time.LocalDate;

public final class TransactionDate extends LocalDateVO {
    public TransactionDate(LocalDate value) {
        super(value);
    }
}
