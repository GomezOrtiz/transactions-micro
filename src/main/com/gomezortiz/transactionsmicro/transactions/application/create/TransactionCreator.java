package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.gomezortiz.transactionsmicro.accounts.application.find.AccountFinder;
import com.gomezortiz.transactionsmicro.accounts.application.update.AccountUpdater;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class TransactionCreator {

    private final TransactionRepository repository;
    private final AccountFinder accountFinder;
    private final AccountUpdater accountUpdater;

    @Transactional
    public void create(TransactionCreateRequest request) {

        Assert.notNull(request, "Transaction request cannot be empty");

        Transaction transaction = request.toPendingTransaction();
        Account account = accountFinder.find(new AccountIban(transaction.accountIban().value()));

        Double balance = calculateBalanceAfterFee(
                transaction.amount().value(),
                transaction.fee().value(),
                account.balance().value()
        );

        if(balance < 0) {
            throw new TransactionNotValidException(account.balance().value(), transaction.amount().value());
        }

        accountUpdater.updateBalance(account.iban(), new AccountBalance(balance));

        repository.create(transaction);
    }

    private Double calculateBalanceAfterFee(Double amount, Double fee, Double balance) {
        Double feeAmount = null != fee ? Math.abs(amount) * (fee / 100) : 0;
        return amount > 0 ? balance + amount - feeAmount : balance - Math.abs(amount) - feeAmount;
    }
}