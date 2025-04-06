package com.test.fabrick.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.fabrick.service.FabrickService;
import com.test.fabrick.utils.InputValidator;

@RestController
@RequestMapping("/api/fabrick")
public class FabrickController {

    private final FabrickService fabrickService;

    public FabrickController(FabrickService fabrickService) {
        this.fabrickService = fabrickService;
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<String> getBalance(@PathVariable String accountId) {
        InputValidator.validateAccountId(accountId);
        return fabrickService.getBalance(accountId);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public <T> ResponseEntity<String> getTransactions(
            @PathVariable T accountId,
            @RequestParam T fromAccountingDate,
            @RequestParam T toAccountingDate) {
        InputValidator.validateAccountId(accountId.toString());
        InputValidator.validateDate(fromAccountingDate.toString());
        InputValidator.validateDate(toAccountingDate.toString());

        return fabrickService.getTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    @PostMapping("/accounts/{accountId}/payments/money-transfers")
    public <T> ResponseEntity<String> makeMoneyTransfer(
            @PathVariable T accountId,
            @RequestBody Map<String, Object> request) {
        InputValidator.validateAccountId(accountId.toString());
        InputValidator.validateMoneyTransferRequest(request);

        return fabrickService.makeMoneyTransfer(accountId, request);
    }
}
