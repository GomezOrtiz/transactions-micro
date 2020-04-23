package com.gomezortiz.transactionsmicro.accounts.application.update;

import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public final class AccountUpdater {

    private final AccountRepository repository;

    public void updateBalance(AccountIban iban, AccountBalance newBalance) {

        Assert.notNull(iban, "Iban cannot be null");
        Assert.notNull(newBalance, "New balance cannot be null");
        Assert.isTrue(newBalance.value() >= 0, "Account remaining balance has to be zero or more");

        repository.updateBalance(iban, newBalance);
    }
}
