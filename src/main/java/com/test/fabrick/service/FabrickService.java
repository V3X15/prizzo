package com.test.fabrick.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.fabrick.config.FabrickProperties;
import com.test.fabrick.utils.FabrickUrlBuilder;

@Service
public class FabrickService {

    private final RestTemplate restTemplate;
    private final FabrickProperties properties;
    private final FabrickUrlBuilder urlBuilder;
    
    private static final Logger logger = LoggerFactory.getLogger(FabrickService.class);

    public FabrickService(RestTemplate restTemplate, FabrickProperties properties, FabrickUrlBuilder urlBuilder) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.urlBuilder = urlBuilder;
    }

    public ResponseEntity<String> getBalance(String accountId) {
    	logger.info("chiamata /getBalance");
    	
        String url = urlBuilder.build(accountId, "balance");
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET, entity);
		return response;
    }

    public <T> ResponseEntity<String> getTransactions(T accountId, T fromAccountingDate, T toAccountingDate) {
    	logger.info("chiamata /getTransactions");
    	
        String[][] queryParams = {
            {"fromAccountingDate", (String) fromAccountingDate},
            {"toAccountingDate", (String) toAccountingDate}
        };
        String url = urlBuilder.buildWithQuery(accountId, new String[] { "transactions" }, queryParams);
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET, entity);
		return response;
    }

    public <T> ResponseEntity<String> makeMoneyTransfer(T accountId, Map<String, Object> request) {
    	logger.info("chiamata /makeMoneyTransfer");
    	
        String url = urlBuilder.build(accountId, "payments", "money-transfers");
        HttpHeaders headers = createHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = sendRequest(url, HttpMethod.POST, entity);
		return response;
    }

    private ResponseEntity<String> sendRequest(String url, HttpMethod method, HttpEntity<?> entity) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
            	 logger.info("STATUS CODE: " + response.getStatusCode());
            	 logger.info("RESPONSE CHIAMATA : " + response.toString());
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Errore nella richiesta.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore imprevisto: " + e.getMessage());
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", "S2S");
        headers.set("Api-Key", properties.getApiKey());
        return headers;
    }
}
