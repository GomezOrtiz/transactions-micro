package com.gomezortiz.transactionsmicro.accounts.domain.model;

import com.gomezortiz.transactionsmicro.shared.domain.model.baseVO.DoubleVO;

public final class AccountBalance extends DoubleVO {
    public AccountBalance(Double value) {
        super(value);
    }
}
