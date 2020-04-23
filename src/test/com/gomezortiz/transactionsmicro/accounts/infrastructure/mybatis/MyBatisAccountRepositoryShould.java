package com.gomezortiz.transactionsmicro.accounts.infrastructure.mybatis;

import com.gomezortiz.transactionsmicro.accounts.domain.model.*;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.accounts.infrastructure.mybatis.repository.MyBatisAccountRepository;
import com.gomezortiz.transactionsmicro.shared.baseVO.Iban;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.shared.infrastructure.InfrastructureTestCase;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(MyBatisAccountRepository.class)
public class MyBatisAccountRepositoryShould extends InfrastructureTestCase {

    private Account account;
    private Collection<Transaction> transactions = new ArrayList<>();

    @Autowired
    AccountRepository repository;

    @BeforeEach
    void setUp() {
        account = AccountMother.random("ES");
        repository.create(account);
    }

    @Test
    void should_find_by_iban() {

        // WHEN
        Account foundAccount = repository.findByIban(account.iban()).orElse(null);

        // THEN
        assertEquals(account.iban(), foundAccount.iban(), "IBAN should be the expected");
        assertEquals(account.balance(), foundAccount.balance(), "Balance should be the expected");
    }

    @Test
    void update_balance() {

        // GIVEN
        Double balance = BigDecimal.valueOf(account.balance().value() + 50)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        AccountBalance newBalance = AccountBalanceMother.create(balance);

        // WHEN
        repository.updateBalance(account.iban(), newBalance);

        // THEN
        Account updatedAccount = repository.findByIban(account.iban()).orElse(null);
        assertEquals(newBalance, updatedAccount.balance(), "Balance should be the expected");
    }
}
