package com.test.fabrick.prizzo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FabrickService {
    
    private static final String BASE_URL = "https://sandbox.platfr.io";
    private static final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
    private static final Logger logger = LoggerFactory.getLogger(FabrickService.class);

    @Autowired
    private final RestTemplate restTemplate;

    public FabrickService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getBalance(String accountId) {
        String url = BASE_URL + "/api/gbs/banking/v4.0/accounts/" + accountId + "/balance";
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return sendRequest(url, HttpMethod.GET, entity);
    }

    @SuppressWarnings("deprecation")
    public ResponseEntity<String> getTransactions(String accountId, String fromAccountingDate, String toAccountingDate) {
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate)
                .buildAndExpand(accountId)
                .toString();
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return sendRequest(url, HttpMethod.GET, entity);
    }

    public ResponseEntity<String> makeMoneyTransfer(String accountId, Map<String, Object> request) {
        String url = BASE_URL + "/api/gbs/banking/v4.0/accounts/" + accountId + "/payments/money-transfers";
        HttpHeaders headers = createHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        return sendRequest(url, HttpMethod.POST, entity);
    }

    private ResponseEntity<String> sendRequest(String url, HttpMethod method, HttpEntity<?> entity) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                logger.error("Errore nella richiesta: {}", response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body("Errore nella richiesta.");
            }
        } catch (Exception e) {
            logger.error("Errore imprevisto nella richiesta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore imprevisto: " + e.getMessage());
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", "S2S");
        headers.set("Api-Key", API_KEY);
        return headers;
    }
}
