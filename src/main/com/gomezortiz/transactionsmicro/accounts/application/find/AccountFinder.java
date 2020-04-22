package com.gomezortiz.transactionsmicro.accounts.application.find;

import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.shared.domain.model.baseVO.Iban;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AccountFinder {

    private final AccountRepository repository;

    public Account find(Iban iban) {
        return repository.findByIban(iban).orElseThrow(() -> new AccountNotFoundException(iban.value()));
    }
}
