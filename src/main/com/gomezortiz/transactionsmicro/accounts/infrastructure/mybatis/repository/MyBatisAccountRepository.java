package com.gomezortiz.transactionsmicro.accounts.infrastructure.mybatis.repository;

import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.accounts.infrastructure.mybatis.mapper.MyBatisAccountMapper;
import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisAccountRepository implements AccountRepository {

    private final MyBatisAccountMapper mapper;

    @Override
    public Collection<Account> findAll() {
        return mapper.findAll();
    }

    @Override
    public Optional<Account> findByIban(AccountIban iban) {
        return mapper.findByIban(iban);
    }

    @Override
    public void updateBalance(AccountIban iban, AccountBalance newBalance) {
        mapper.updateBalance(iban, newBalance);
    }

    @Override
    public void create(Account account) {
        mapper.create(account);
    }
}
