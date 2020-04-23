package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.gomezortiz.transactionsmicro.accounts.application.find.AccountFinder;
import com.gomezortiz.transactionsmicro.accounts.application.update.AccountUpdater;
import com.gomezortiz.transactionsmicro.accounts.domain.model.*;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.shared.domain.DoubleMother;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionAccountIbanMother;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionIban;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TransactionCreator.class, AccountFinder.class, AccountUpdater.class})
public class TransactionCreatorShould {

    @MockBean
    TransactionRepository repository;

    @MockBean
    AccountRepository accountRepository;

    @Autowired
    TransactionCreator creator;

    @Test
    void create_a_valid_transaction_with_id() {

        // GIVEN
        TransactionCreateRequest request = TransactionCreateRequestMother.random(true, true,"ES");
        Account account = AccountMother.withBalance("ES", Math.abs(request.amount()) * 2);
        when(accountRepository.findByIban(AccountIbanMother.create(request.accountIban()))).thenReturn(Optional.of(account));

        // WHEN
        creator.create(request);

        // THEN
        verify(accountRepository, times(1)).updateBalance(eq(account.iban()), any(AccountBalance.class));
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository, times(1)).create(transactionCaptor.capture());
        assertCreatedTransaction(request, transactionCaptor.getValue());
    }

    @Test
    void create_a_valid_transaction_without_id() {

        // GIVEN
        TransactionCreateRequest request = TransactionCreateRequestMother.random(false, false, "ES");
        Account account = AccountMother.random("ES");
        when(accountRepository.findByIban(new AccountIban(request.accountIban()))).thenReturn(Optional.of(account));

        // WHEN
        creator.create(request);

        // THEN
        verify(accountRepository, times(1)).updateBalance(eq(account.iban()), any(AccountBalance.class));
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository, times(1)).create(transactionCaptor.capture());
        assertCreatedTransaction(request, transactionCaptor.getValue());
    }

    @Test
    void reject_transaction_if_balance_not_enough() {

        // GIVEN
        TransactionCreateRequest request = TransactionCreateRequestMother.random(true, true,"ES");
        Account account = AccountMother.withBalance("ES", DoubleMother.random(2, 0, Math.abs(request.amount().intValue())));
        String expectedError = String.format("Transaction is not valid. Account balance %s is not enough to transfer %s", account.balance().value(), request.amount());
        when(accountRepository.findByIban(new AccountIban(request.accountIban()))).thenReturn(Optional.of(account));

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

    private void assertCreatedTransaction(TransactionCreateRequest expected, Transaction actual) {
        assertNotNull(actual.reference(), "Reference should not be null");
        assertEquals(expected.accountIban(), actual.accountIban().value(), "Account IBAN should be the expected");
        assertEquals(expected.amount(), actual.amount().value(), "Amount should be the expected");
        assertEquals(expected.fee(), actual.fee().value(), "Fee should be the expected");
        assertEquals(expected.description(), actual.description().value(), "Description should be the expected");
        assertEquals(expected.date(), actual.date().value(), "Date should be the expected");
    }
}
