package com.gomezortiz.transactionsmicro.transactions.application.status;

import com.gomezortiz.transactionsmicro.accounts.application.find.AccountFinder;
import com.gomezortiz.transactionsmicro.accounts.application.update.AccountUpdater;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountMother;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.shared.domain.model.DoubleMother;
import com.gomezortiz.transactionsmicro.shared.domain.model.channel.Channel;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequest;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequestMother;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreator;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionNotValidException;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TransactionStatusProcessor.class)
public class TransactionStatusProcessorShould {

    @MockBean
    TransactionRepository repository;

    @Autowired
    TransactionStatusProcessor processor;

    @Test
    void process_transaction_not_stored() {

        // GIVEN
        Channel channel = Channel.CLIENT;
        Transaction transaction = TransactionMother.randomForSpain();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.emptyList());

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.INVALID, response.status(), "Status should be the expected");
        assertEquals(null, response.amount(), "Amount should be the expected");
        assertEquals(null, response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_not_internal_before_today() {

        // GIVEN
        Channel channel = Channel.ATM;
        Transaction transaction = TransactionMother.beforeToday();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.SETTLED, response.status(), "Status should be the expected");
        assertEquals(getAmountMinusFee(transaction), response.amount(), "Amount should be the expected");
        assertEquals(null, response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_internal_before_today() {

        // GIVEN
        Channel channel = Channel.INTERNAL;
        Transaction transaction = TransactionMother.beforeToday();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.SETTLED, response.status(), "Status should be the expected");
        assertEquals(transaction.amount().value(), response.amount(), "Amount should be the expected");
        assertEquals(transaction.fee().value(), response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_not_internal_today() {

        // GIVEN
        Channel channel = Channel.CLIENT;
        Transaction transaction = TransactionMother.randomForSpain();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.PENDING, response.status(), "Status should be the expected");
        assertEquals(getAmountMinusFee(transaction), response.amount(), "Amount should be the expected");
        assertEquals(null, response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_internal_today() {

        // GIVEN
        Channel channel = Channel.INTERNAL;
        Transaction transaction = TransactionMother.randomForSpain();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.PENDING, response.status(), "Status should be the expected");
        assertEquals(transaction.amount().value(), response.amount(), "Amount should be the expected");
        assertEquals(transaction.fee().value(), response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_client_after_today() {

        // GIVEN
        Channel channel = Channel.CLIENT;
        Transaction transaction = TransactionMother.afterToday();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.FUTURE, response.status(), "Status should be the expected");
        assertEquals(getAmountMinusFee(transaction), response.amount(), "Amount should be the expected");
        assertEquals(null, response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_atm_after_today() {

        // GIVEN
        Channel channel = Channel.ATM;
        Transaction transaction = TransactionMother.afterToday();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.PENDING, response.status(), "Status should be the expected");
        assertEquals(getAmountMinusFee(transaction), response.amount(), "Amount should be the expected");
        assertEquals(null, response.fee(), "Fee should be the expected");
    }

    @Test
    void process_transaction_internal_after_today() {

        // GIVEN
        Channel channel = Channel.INTERNAL;
        Transaction transaction = TransactionMother.afterToday();
        when(repository.findByCriteria(any(TransactionSearchCriteria.class)))
                .thenReturn(Collections.singletonList(transaction));

        // WHEN
        TransactionStatusResponse response = processor.process(transaction.reference(), channel);

        // THEN
        assertEquals(transaction.reference().value(), response.reference(), "Reference should be the expected");
        assertEquals(TransactionStatus.FUTURE, response.status(), "Status should be the expected");
        assertEquals(transaction.amount().value(), response.amount(), "Amount should be the expected");
        assertEquals(transaction.fee().value(), response.fee(), "Fee should be the expected");
    }

    private Double getAmountMinusFee(Transaction transaction) {
        Double feeAmount = null != transaction.fee().value() ? Math.abs(transaction.amount().value()) * (transaction.fee().value() / 100) : 0;
        return transaction.amount().value() - feeAmount;
    }
}
