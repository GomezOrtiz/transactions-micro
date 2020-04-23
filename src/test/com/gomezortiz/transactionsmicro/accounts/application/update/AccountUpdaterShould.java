package com.gomezortiz.transactionsmicro.accounts.application.update;

import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalanceMother;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIbanMother;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = AccountUpdater.class)
@ActiveProfiles("test")
public class AccountUpdaterShould {

    @MockBean
    AccountRepository repository;

    @Autowired
    AccountUpdater updater;

    @Test
    void should_update_account() {

        // GIVEN
        AccountIban iban = AccountIbanMother.random("ES");
        AccountBalance balance = AccountBalanceMother.random(0, 100);

        // WHEN
        updater.updateBalance(iban, balance);

        // THEN
        verify(repository, times(1)).updateBalance(iban, balance);
    }

    @Test
    void should_fail_update_account_negative_balance() {

        // GIVEN
        AccountIban iban = AccountIbanMother.random("ES");
        AccountBalance balance = AccountBalanceMother.random(-100, -1);
        String expectedError = "Account remaining balance has to be zero or more";

        // WHEN
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            updater.updateBalance(iban, balance);
        });

        // THEN
        verify(repository, never()).updateBalance(iban, balance);
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");
    }

    @Test
    void should_fail_update_account_null_iban() {

        // GIVEN
        AccountBalance balance = AccountBalanceMother.random(0, 100);
        String expectedError = "Iban cannot be null";

        // WHEN
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            updater.updateBalance(null, balance);
        });

        // THEN
        verify(repository, never()).updateBalance(any(), eq(balance));
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");
    }

    @Test
    void should_fail_update_account_null_balance() {

        // GIVEN
        AccountIban iban = AccountIbanMother.random("ES");
        String expectedError = "New balance cannot be null";

        // WHEN
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            updater.updateBalance(iban, null);
        });

        // THEN
        verify(repository, never()).updateBalance(eq(iban), any());
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");
    }
}
