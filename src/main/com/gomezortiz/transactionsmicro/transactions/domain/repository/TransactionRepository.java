package com.gomezortiz.transactionsmicro.transactions.domain.repository;

import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;

import java.util.Collection;

public interface TransactionRepository {

    void create(Transaction transaction);
    Collection<Transaction> findByCriteria(TransactionSearchCriteria criteria);
}
