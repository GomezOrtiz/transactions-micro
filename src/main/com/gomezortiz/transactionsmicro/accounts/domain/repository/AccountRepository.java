package com.gomezortiz.transactionsmicro.accounts.domain.repository;

import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByIban(AccountIban iban);
    void updateBalance(AccountIban iban, AccountBalance newBalance);
    void create(Account account);
}
