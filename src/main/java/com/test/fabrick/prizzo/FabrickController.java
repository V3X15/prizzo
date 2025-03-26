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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/fabrick")
public class FabrickController {
	

    private RestTemplate restTemplate = new RestTemplate();
	
    private static final String BASE_URL = "https://sandbox.platfr.io";
    private static final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
    private static final String ACCOUNT_ID = "14537780";
    
    private static final Logger logger = LoggerFactory.getLogger(FabrickController.class);

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", "S2S");
        headers.set("Api-Key", API_KEY);
        return headers;
    }
    
    @GetMapping("/balance")
    public ResponseEntity<String> getBalance() {
        logger.info("Richiesta bilancio dell'account: {}", ACCOUNT_ID);

        String url = BASE_URL + "/api/gbs/banking/v4.0/accounts/" + ACCOUNT_ID + "/balance";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Bilancio recuperato correttamente.");
                return ResponseEntity.ok(response.getBody());
            } else {
                logger.error("Errore nel recupero del bilancio: " + response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body("Errore nel recupero del bilancio.");
            }
        } catch (Exception e) {
            logger.error("Errore imprevisto nel recupero del bilancio: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore imprevisto: " + e.getMessage());
        }
    }

    
    @GetMapping("/transactions")
    public ResponseEntity<String> getTransactions(@RequestParam String fromAccountingDate, @RequestParam String toAccountingDate) {
    	
    	logger.info("Richiesta transazioni dal {} al {}", fromAccountingDate, toAccountingDate);
    	
        String url = BASE_URL + "/api/gbs/banking/v4.0/accounts/" + ACCOUNT_ID + "/transactions?fromAccountingDate=" + fromAccountingDate + "&toAccountingDate=" + toAccountingDate;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        
        try {
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        if (response.getStatusCode() == HttpStatus.OK) {
	        	logger.info("Transazioni recuperate correttamente.");
	        	return ResponseEntity.ok(response.getBody());
	        }else {
                logger.error("Errore nel recupero delle transazioni: " + response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body("Errore nel recupero del bilancio.");
            }
        } catch (Exception e) {
            logger.error("Errore imprevisto nel recupero delle transazioni: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore imprevisto: " + e.getMessage());
            
        }
    }
    
    @PostMapping("/money-transfer")
    public ResponseEntity<String> makeMoneyTransfer(@RequestBody Map<String, Object> request) {
    	
    	logger.info("Crezione bonifico per l'account: {}", ACCOUNT_ID);
    	
        String url = BASE_URL + "/api/gbs/banking/v4.0/accounts/" + ACCOUNT_ID + "/payments/money-transfers";
        HttpHeaders headers = createHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        try {
        	ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        	if (response.getStatusCode() == HttpStatus.OK) {
        		logger.info("Bonifico generato correttamente.");
        		return ResponseEntity.ok(response.getBody());
        	}else {
        		logger.info("Errore nella generazione bonifico: " + response.getStatusCode());
        		return ResponseEntity.ok(response.getBody());
        	}
        
        } catch (Exception e) {
            logger.error("Errore imprevisto nella creazione bonifico: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore imprevisto: " + e.getMessage());
        }
    }
}