package com.gomezortiz.transactionsmicro.transactions.rest;

import antlr.StringUtils;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderBy;
import com.gomezortiz.transactionsmicro.shared.criteria.OrderType;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreateRequest;
import com.gomezortiz.transactionsmicro.transactions.application.create.TransactionCreator;
import com.gomezortiz.transactionsmicro.transactions.application.find.TransactionFinder;
import com.gomezortiz.transactionsmicro.transactions.application.status.Channel;
import com.gomezortiz.transactionsmicro.transactions.application.status.TransactionStatusProcessor;
import com.gomezortiz.transactionsmicro.transactions.domain.model.Transaction;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionIban;
import com.gomezortiz.transactionsmicro.transactions.domain.model.TransactionReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionCreator creator;
    private final TransactionFinder finder;
    private final TransactionStatusProcessor statusProcessor;

    @GetMapping("/find")
    public ResponseEntity findByIban(
            @RequestParam String iban,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "DESC") String orderType
    ) {

        Collection<Transaction> transactions = finder.findByIban(new TransactionIban(iban), new OrderBy(orderBy), OrderType.valueOf(orderType.toUpperCase()));
        Collection<TransactionResponse> response = transactions.stream().map(transaction -> TransactionResponse.fromTransaction(transaction)).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity checkStatus(
            @RequestParam String reference,
            @RequestParam String channel
    ) {
        return ResponseEntity.ok(statusProcessor.process(new TransactionReference(reference), Channel.valueOf(channel)));
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TransactionCreateRequest request) {
        creator.create(request);
    }

}
