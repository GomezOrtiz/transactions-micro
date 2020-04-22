package com.gomezortiz.transactionsmicro.transactions.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.baseVO.DoubleVO;

public final class TransactionAmount extends DoubleVO {
    public TransactionAmount(Double value) {
        //TODO: Validation
        super(value);
    }
}
