package com.gomezortiz.transactionsmicro.transactions.application.find;

import com.gomezortiz.transactionsmicro.accounts.application.find.AccountFinder;
import com.gomezortiz.transactionsmicro.accounts.application.update.AccountUpdater;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountMother;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.shared.domain.model.DoubleMother;
import com.gomezortiz.transactionsmicro.shared.domain.model.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.domain.model.criteria.OrderType;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequest;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequestMother;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreator;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionNotValidException;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
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

@SpringBootTest(classes = TransactionFinder.class)
public class TransactionFinderShould {

    @MockBean
    TransactionRepository repository;

    @Autowired
    TransactionFinder finder;

    @Test
    void should_find_by_criteria() {

        // GIVEN
        TransactionIban iban = TransactionAccountIbanMother.random("ES");
        OrderBy orderBy = new OrderBy("amount");
        OrderType orderType = OrderType.DESC;

        // WHEN
        finder.findByIban(iban, orderBy, orderType);

        // THEN
        ArgumentCaptor<TransactionSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(TransactionSearchCriteria.class);
        verify(repository, times(1)).findByCriteria(criteriaCaptor.capture());
        TransactionSearchCriteria builtCriteria = criteriaCaptor.getValue();
        assertEquals(iban , builtCriteria.iban(), "El IBAN del objeto criteria debería ser el esperado");
        assertEquals(orderBy, builtCriteria.orderBy(), "El orderBy del objeto criteria debería ser el esperado");
        assertEquals(orderType, builtCriteria.orderType(), "El orderType del objeto criteria debería ser el esperado");
    }

    @Test
    void fail_if_iban_is_null() {

        // GIVEN
        OrderBy orderBy = new OrderBy("amount");
        OrderType orderType = OrderType.DESC;
        String expectedError = String.format("Transaction IBAN cannot be empty");

        // WHEN
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            finder.findByIban(null, orderBy, orderType);
        });

        // THEN
        assertEquals(expectedError, e.getMessage(), "Error message should be the expected");

    }
}
