package com.gomezortiz.transactionsmicro.accounts.rest;

import com.gomezortiz.transactionsmicro.accounts.application.find.AccountFinder;
import com.gomezortiz.transactionsmicro.accounts.domain.model.Account;
import com.gomezortiz.transactionsmicro.accounts.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountFinder finder;

    @GetMapping("/all")
    public ResponseEntity<Collection<AccountResponse>> findAll() {
        Collection<Account> accounts = finder.findAll();
        Collection<AccountResponse> response = accounts.stream().map(account -> AccountResponse.fromAccount(account)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
