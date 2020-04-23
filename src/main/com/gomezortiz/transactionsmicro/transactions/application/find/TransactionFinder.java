package com.gomezortiz.transactionsmicro.transactions.application.find;

import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionIban;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TransactionFinder {

    private final TransactionRepository repository;

    public Collection<Transaction> findByIban(TransactionIban iban, OrderBy orderBy, OrderType orderType) {
        Assert.notNull(iban, "Transaction IBAN cannot be empty");
        TransactionSearchCriteria criteria = TransactionSearchCriteria.builder()
                .iban(iban)
                .orderBy(orderBy)
                .orderType(orderType)
                .build();
        return repository.findByCriteria(criteria);
    }
}
