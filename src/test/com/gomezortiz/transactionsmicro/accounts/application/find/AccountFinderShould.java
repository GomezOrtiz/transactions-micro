package com.gomezortiz.transactionsmicro.accounts.application.find;

import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIbanMother;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountMother;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest(classes = AccountFinder.class)
@ActiveProfiles("test")
public class AccountFinderShould {

    @MockBean
    AccountRepository repository;

    @Autowired
    AccountFinder finder;

    @Test
    void should_find_by_iban() {

        // GIVEN
        Account account = AccountMother.random("ES");
        when(repository.findByIban(account.iban())).thenReturn(Optional.of(account));

        // WHEN
        Account foundAccount = finder.find(account.iban());

        // THEN
        verify(repository, times(1)).findByIban(account.iban());
    }

    @Test
    void should_throw_account_not_found_exception() {

        // GIVEN
        AccountIban iban = AccountIbanMother.random("ES");
        when(repository.findByIban(iban)).thenReturn(Optional.empty());
        String expectedError = "No account with IBAN " + iban.value();

        // WHEN
        AccountNotFoundException e = assertThrows(AccountNotFoundException.class, () -> {
            finder.find(iban);
        });

        // THEN
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");
    }

    @Test
    void should_fail_find_by_iban() {

        // GIVEN
        String expectedError = "Iban cannot be empty";

        // WHEN
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            finder.find(null);
        });

        // THEN
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");
    }
}
