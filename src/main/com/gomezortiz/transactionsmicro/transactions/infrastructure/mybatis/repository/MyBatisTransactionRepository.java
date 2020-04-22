package com.gomezortiz.transactionsmicro.transactions.infrastructure.mybatis.repository;

import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import com.gomezortiz.transactionsmicro.transactions.infrastructure.mybatis.mapper.MyBatisTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class MyBatisTransactionRepository implements TransactionRepository {

    private final MyBatisTransactionMapper mapper;

    @Override
    public void create(Transaction transaction) {
        mapper.create(transaction);
    }

    @Override
    public Collection<Transaction> findByCriteria(TransactionSearchCriteria criteria) {
        return mapper.findByCriteria(criteria);
    }
}
