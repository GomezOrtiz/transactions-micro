package com.gomezortiz.transactionsmicro.transactions.application.find;

import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionAccountIbanMother;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionIban;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        assertEquals(iban , builtCriteria.iban(), "Criteria IBAN should be the expected");
        assertEquals(orderBy, builtCriteria.orderBy(), "Criteria OrderBy should be the expected");
        assertEquals(orderType, builtCriteria.orderType(), "Criteria orderType should be the expected");
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
