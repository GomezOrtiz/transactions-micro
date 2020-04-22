package com.gomezortiz.transactionsmicro.transactions.infrastructure.mybatis.mapper;

import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface MyBatisTransactionMapper {

    void create(@Param("transaction") Transaction transaction);
    Collection<Transaction> findByCriteria(@Param("criteria") TransactionSearchCriteria criteria);
}
