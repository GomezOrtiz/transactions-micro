package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.gomezortiz.transactionsmicro.accounts.application.findBalance.AccountBalanceFinder;
import com.gomezortiz.transactionsmicro.shared.domain.model.DoubleMother;
import com.gomezortiz.transactionsmicro.transactions.domain.exception.TransactionNotValidException;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionAccountIban;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TransactionCreator.class)
public class TransactionCreatorShould {

    @MockBean
    TransactionRepository repository;

    @MockBean
    AccountBalanceFinder finder;

    @Autowired
    TransactionCreator creator;

    @Test
    void create_a_valid_transaction_with_id() {

        // GIVEN
        TransactionCreateRequest request = TransactionCreateRequestMother.random(true, true,"ES");
        when(finder.findByIban(new TransactionAccountIban(request.accountIban()))).thenReturn(request.amount() * 2);

        // WHEN
        creator.create(request);

        // THEN
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository, times(1)).create(transactionCaptor.capture());
        Transaction createdTransaction = transactionCaptor.getValue();
        //TODO: Asserts for createdTransaction if needed
    }

    @Test
    void create_a_valid_transaction_without_id() {

        // GIVEN
        TransactionCreateRequest request = TransactionCreateRequestMother.random(false, false, "ES");
        when(finder.findByIban(new TransactionAccountIban(request.accountIban()))).thenReturn(DoubleMother.random(2, 0 ,1000));

        // WHEN
        creator.create(request);

        // THEN
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository, times(1)).create(transactionCaptor.capture());
        Transaction createdTransaction = transactionCaptor.getValue();
        //TODO: Asserts for createdTransaction if needed
    }

    @Test
    void reject_transaction_if_balance_not_enough() {

        // GIVEN
        TransactionCreateRequest request = TransactionCreateRequestMother.random(true, true,"ES");
        Double accountBalance = DoubleMother.random(2, 0, request.amount().intValue());
        String expectedError = String.format("Transaction is not valid. Account balance %s is not enough to transfer %s", accountBalance, request.amount());
        when(finder.findByIban(new TransactionAccountIban(request.accountIban()))).thenReturn(accountBalance);

        // WHEN
        Exception e = assertThrows(TransactionNotValidException.class, () -> {
            creator.create(request);
        });

        // THEN
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");
    }

    @Test
    void fail_if_request_is_null() {

        // GIVEN
        String expectedError = String.format("Transaction request cannot be empty");

        // WHEN
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            creator.create(null);
        });

        // THEN
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");

    }
}
