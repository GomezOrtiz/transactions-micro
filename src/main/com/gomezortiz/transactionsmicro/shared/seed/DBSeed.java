package com.gomezortiz.transactionsmicro.shared.seed;

import com.github.javafaker.Faker;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountBalance;
import com.gomezortiz.transactionsmicro.accounts.domain.model.AccountIban;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import com.gomezortiz.transactionsmicro.shared.baseVO.OffsetDateTimeVO;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.naming.TransactionRef;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DBSeed {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @EventListener
    @Transactional
    public void seedTables(ContextRefreshedEvent event) {

        Faker faker = new Faker(new Locale("es"));

        for (int i = 0; i < 20; i++) {
            AccountIban iban = new AccountIban(faker.finance().iban("ES"));
            AccountBalance balance = new AccountBalance(faker.number().randomDouble(2, 100, 1000));
            Account account = new Account(iban, balance);
            accountRepository.create(account);

            for (int j = 0; j < 3; j++) {
                TransactionReference reference = new TransactionReference(UUID.randomUUID().toString());
                TransactionDate date = new TransactionDate(
                        OffsetDateTime.ofInstant(
                                faker.date().between(
                                        Date.from(OffsetDateTime.now().minusYears(5).toInstant()),
                                        new Date()
                                ).toInstant(),
                                ZoneId.of("UTC")
                        )
                );
                TransactionAmount amount = new TransactionAmount(faker.number().randomDouble(2, 1, 100));
                TransactionDescription description = new TransactionDescription(faker.lorem().sentence(2));
                Transaction transaction = new Transaction(reference, new TransactionIban(iban.value()), date, amount, new TransactionFee(3.18), description);
                transactionRepository.create(transaction);
            }
        }
    }
}
