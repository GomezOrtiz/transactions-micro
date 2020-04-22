package com.gomezortiz.transactionsmicro.transactions.application.create;

import com.gomezortiz.transactionsmicro.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class TransactionCreator {

    private final TransactionRepository repository;

    public void create(TransactionCreateRequest request) {
        //TODO: Implement business logic
        repository.create(request.toPendingTransaction());
    }
}