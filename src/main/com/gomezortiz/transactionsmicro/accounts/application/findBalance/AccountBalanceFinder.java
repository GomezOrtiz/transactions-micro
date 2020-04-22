package com.gomezortiz.transactionsmicro.accounts.application.findBalance;

import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AccountBalanceFinder {

    private final AccountRepository repository;

    public Double findByIban(AccountIban iban) {
        return repository.findByIban(iban);
    }
}
