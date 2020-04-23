package com.gomezortiz.transactionsmicro.transactions.application.status;

import com.gomezortiz.transactionsmicro.accounts.application.find.AccountFinder;
import com.gomezortiz.transactionsmicro.accounts.application.update.AccountUpdater;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.shared.domain.model.channel.Channel;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequest;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionNotValidException;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionReference;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionStatus;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public final class TransactionStatusProcessor {

    private final TransactionRepository repository;

    public TransactionStatusResponse process(TransactionReference reference, Channel channel) {

        Assert.notNull(reference, "Transaction reference cannot be empty");

        TransactionSearchCriteria criteria = TransactionSearchCriteria.builder()
                .reference(reference)
                .build();
        Collection<Transaction> transactions = repository.findByCriteria(criteria);

        if(transactions.isEmpty()) {
            return new TransactionStatusResponse(reference.value(), TransactionStatus.INVALID, null, null);
        }

        Transaction transaction = transactions.iterator().next();
        TransactionStatus status = processStatus(transaction, channel);
        Double amount = processAmount(transaction, channel);
        Double fee = channel.equals(Channel.INTERNAL) ? transaction.fee().value() : null;

        return new TransactionStatusResponse(reference.value(), status, amount, fee);
    }

    private Double processAmount(Transaction transaction, Channel channel) {

        Double amount = null;

        if(channel.equals(Channel.CLIENT) || channel.equals(Channel.ATM)) {
            amount = getAmountMinusFee(transaction);
        } else if(channel.equals(Channel.INTERNAL)){
            amount = transaction.amount().value();
        }

        return amount;
    }

    private Double getAmountMinusFee(Transaction transaction) {
        Double feeAmount = null != transaction.fee().value() ? Math.abs(transaction.amount().value()) * (transaction.fee().value() / 100) : 0;
        return transaction.amount().value() - feeAmount;
    }

    private TransactionStatus processStatus(Transaction transaction, Channel channel) {

        TransactionStatus status = null;

        if(isBeforeToday(transaction.date().value())) {
            status = TransactionStatus.SETTLED;
        } else if(isAfterToday(transaction.date().value())) {
            if(channel.equals(Channel.CLIENT) || channel.equals(Channel.INTERNAL)) {
                status = TransactionStatus.FUTURE;
            } else if(channel.equals(Channel.ATM)) {
                status = TransactionStatus.PENDING;
            }
        } else {
            status = TransactionStatus.PENDING;
        }

        return status;
    }

    private boolean isBeforeToday(LocalDate date) {
        return date.atStartOfDay().isBefore(LocalDate.now().atStartOfDay());
    }
    private boolean isAfterToday(LocalDate date) {
        return date.atStartOfDay().isAfter(LocalDate.now().atStartOfDay());
    }
}