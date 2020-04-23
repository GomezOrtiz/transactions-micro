package com.gomezortiz.transactionsmicro.transactions.domain.criteria;

import com.gomezortiz.transactionsmicro.shared.criteria.Criteria;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class TransactionSearchCriteria extends Criteria {

    private final TransactionReference reference;
    private final TransactionIban iban;

    @Builder
    public TransactionSearchCriteria(OrderType orderType, OrderBy orderBy, TransactionReference reference, TransactionIban iban, TransactionDate date, TransactionAmount amount, TransactionFee fee, TransactionDescription description) {
        super(orderType, orderBy);
        this.reference = reference;
        this.iban = iban;
    }
}
