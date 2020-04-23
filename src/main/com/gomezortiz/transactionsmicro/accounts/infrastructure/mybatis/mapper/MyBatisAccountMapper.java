package com.gomezortiz.transactionsmicro.accounts.infrastructure.mybatis.mapper;

import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MyBatisAccountMapper {

    Optional<Account> findByIban(@Param("iban") Iban iban);
    void updateBalance(@Param("iban") Iban iban, @Param("balance") AccountBalance newBalance);
    void create(@Param("account") Account account);
}
