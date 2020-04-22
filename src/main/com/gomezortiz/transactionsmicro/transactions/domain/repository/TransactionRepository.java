package com.gomezortiz.transactionsmicro.transactions.domain.repository;

import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;

public interface TransactionRepository {

    void create(Transaction transacion);
}
