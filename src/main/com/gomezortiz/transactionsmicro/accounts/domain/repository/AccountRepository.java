package com.gomezortiz.transactionsmicro.accounts.domain.repository;

import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;

public interface AccountRepository {

    Double findByIban(AccountIban iban);
}
