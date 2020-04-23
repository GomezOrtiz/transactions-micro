package com.gomezortiz.transactionsmicro.accounts.domain.repository;

import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByIban(Iban iban);
    void updateBalance(Iban iban, AccountBalance newBalance);
}
