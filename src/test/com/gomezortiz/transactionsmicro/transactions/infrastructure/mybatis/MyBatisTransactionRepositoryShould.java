package com.gomezortiz.transactionsmicro.transactions.infrastructure.mybatis;

import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.shared.infrastructure.InfrastructureTestCase;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import com.gomezortiz.transactionsmicro.transactions.infrastructure.mybatis.repository.MyBatisTransactionRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Import(MyBatisTransactionRepository.class)
public class MyBatisTransactionRepositoryShould extends InfrastructureTestCase {

    private TransactionIban iban;
    private Collection<Transaction> transactions = new ArrayList<>();

    @Autowired
    TransactionRepository repository;

    @BeforeEach
    void setUp() {
        iban = TransactionAccountIbanMother.random("ES");
        for (int i = 0; i < 3; i++) {
            TransactionAmount amount = TransactionAmountMother.create(Double.valueOf(i));
            Transaction transaction = TransactionMother.withIbanAndAmount(iban, amount);
            repository.create(transaction);
            transactions.add(transaction);
        }
    }

    @Test
    void should_find_by_criteria() {

        // GIVEN
        TransactionSearchCriteria criteria = TransactionSearchCriteria.builder()
                .iban(iban)
                .orderType(OrderType.ASC)
                .orderBy(new OrderBy("amount"))
                .build();

        // WHEN
        Collection<Transaction> foundTransactions = repository.findByCriteria(criteria);

        // THEN
        assertEquals(3, foundTransactions.size(), "Should have found expected number of transactions");

        Iterator<Transaction> transIt = transactions.iterator();
        Iterator<Transaction> foundIt = foundTransactions.iterator();
        while (transIt.hasNext()) {
            Transaction expected = transIt.next();
            Transaction actual = foundIt.next();

            assertEquals(expected.reference(), actual.reference(), "Account reference should be the expected");
            assertEquals(expected.accountIban(), actual.accountIban(), "Account IBAN should be the expected");
            assertEquals(expected.amount(), actual.amount(), "Amount should be the expected");
            assertEquals(expected.fee(), actual.fee(), "Fee should be the expected");
            assertEquals(expected.description(), actual.description(), "Description should be the expected");
            assertEquals(expected.date(), actual.date(), "Date should be the expected");
        }
    }
}
