package com.test.fabrick.prizzo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fabrick")
public class FabrickController {

	private final FabrickService fabrickService;
    private static final Logger logger = LoggerFactory.getLogger(FabrickController.class);

    public FabrickController(FabrickService fabrickService) {
        this.fabrickService = fabrickService;
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String accountId) 
    {
        logger.info("Richiesta bilancio dell'account: {}", accountId);
        return fabrickService.getBalance(accountId);
    }

    @GetMapping("/transactions")
    public ResponseEntity<String> getTransactions(
    		@RequestParam String accountId,
            @RequestParam String fromAccountingDate,
            @RequestParam String toAccountingDate) 
    {
        logger.info("Richiesta transazioni dal {} al {}", fromAccountingDate, toAccountingDate);
        return fabrickService.getTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    @PostMapping("/money-transfer")
    public ResponseEntity<String> makeMoneyTransfer(
    		@RequestParam String accountId, 
    		@RequestBody Map<String, Object> request) 
    {
        logger.info("Creazione bonifico per l'account: {}", accountId);
        return fabrickService.makeMoneyTransfer(accountId, request);
    }
}