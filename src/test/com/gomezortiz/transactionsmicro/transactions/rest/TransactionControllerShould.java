package com.gomezortiz.transactionsmicro.transactions.rest;

import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.shared.domain.DoubleMother;
import com.gomezortiz.transactionsmicro.shared.domain.IbanMother;
import com.gomezortiz.transactionsmicro.shared.rest.RestControllerTestCase;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequest;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreator;
import com.gomezortiz.transactionsmicro.transactions.application.find.TransactionFinder;
import com.gomezortiz.transactionsmicro.transactions.application.status.Channel;
import com.gomezortiz.transactionsmicro.transactions.application.status.TransactionStatusProcessor;
import com.gomezortiz.transactionsmicro.transactions.application.status.TransactionStatusResponse;
import com.gomezortiz.transactionsmicro.transactions.domain.criteria.TransactionSearchCriteria;
import com.gomezortiz.transactionsmicro.transactions.domain.model.*;
import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerShould extends RestControllerTestCase {

    @MockBean
    private TransactionCreator creator;

    @MockBean
    private TransactionFinder finder;

    @MockBean
    private TransactionStatusProcessor statusProcessor;

    @MockBean
    private TransactionRepository repository;

    @Test
    void find_by_iban() throws Exception {

        // GIVEN
        TransactionIban iban = TransactionAccountIbanMother.random("ES");
        OrderBy orderBy = new OrderBy("amount");
        OrderType orderType = OrderType.DESC;
        Transaction transaction = TransactionMother.withIbanAndAmount(iban, TransactionAmountMother.random(0, 100));
        when(finder.findByIban(iban, orderBy, orderType)).thenReturn(Collections.singletonList(transaction));

        // THEN
        String params = "?iban=" + iban.value() + "&orderBy=" + orderBy.value() + "&orderType=" + orderType.value();
        String body = bodyAsJson(Collections.singletonList(TransactionResponse.fromTransaction(transaction)));
        assertResponse("/transaction/find".concat(params), HttpMethod.GET, "", HttpStatus.OK, body);
    }

    @Test
    void check_status() throws Exception {

        // GIVEN
        Transaction transaction = TransactionMother.beforeToday();
        Channel channel = Channel.INTERNAL;
        when(repository.findByCriteria(any(TransactionSearchCriteria.class))).thenReturn(Collections.singleton(transaction));
        TransactionStatusResponse statusResponse = new TransactionStatusResponse(transaction.reference().value(), TransactionStatus.SETTLED, transaction.amount().value(), transaction.fee().value());
        when(statusProcessor.process(transaction.reference(), channel)).thenReturn(statusResponse);

        // THEN
        String params = "?reference=" + transaction.reference().value() + "&channel=" + channel;
        String body = bodyAsJson(statusResponse);
        assertResponse("/transaction/status".concat(params), HttpMethod.GET, "", HttpStatus.OK, body);
    }

    @Test
    void create_transaction() throws Exception {

        // GIVEN
        TransactionCreateRequest request = new TransactionCreateRequest(
                TransactionReferenceMother.random().value(),
                IbanMother.random("ES"),
                OffsetDateTime.parse("2019-07-16T16:55:42.000Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                DoubleMother.random(2, 0, 100),
                3.18,
                "Description"
        );

        // THEN
        assertResponse("/transaction/new", HttpMethod.POST, bodyAsJson(request), HttpStatus.CREATED, "");
        ArgumentCaptor<TransactionCreateRequest> requestCaptor = ArgumentCaptor.forClass(TransactionCreateRequest.class);
        verify(creator, times(1)).create(requestCaptor.capture());
        assertRequest(request, requestCaptor.getValue());
    }

    private void assertRequest(TransactionCreateRequest expected, TransactionCreateRequest actual) {
        assertEquals(expected.reference(), actual.reference(), "Reference should be the expected");
        assertEquals(expected.accountIban(), actual.accountIban(), "Iban should be the expected");
        assertEquals(expected.amount(), actual.amount(), "Amount should be the expected");
        assertEquals(expected.fee(), actual.fee(), "Fee should be the expected");
        assertEquals(expected.date(), actual.date(), "Date should be the expected");
        assertEquals(expected.description(), actual.description(), "Description should be the expected");
    }
}
