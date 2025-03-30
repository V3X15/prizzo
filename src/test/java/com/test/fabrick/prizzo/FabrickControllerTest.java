package com.test.fabrick.prizzo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest()
public class FabrickControllerTest {

    @Autowired
    private FabrickService service;

    private static final String ACCOUNT_ID = "14537780";
    private static final TestLogger tLogger = new TestLogger(FabrickControllerTest.class);  // loggin + scrittura su file
    private static final Logger logger = LoggerFactory.getLogger(FabrickControllerTest.class);

    @Test
    public void testBalance() throws Exception {
    	try {
    		
	        ResponseEntity<String> response = service.getBalance(ACCOUNT_ID);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        logger.debug("Response della chiamata /balance : \n {}", jsonResponse.toString());
	        
    	 }catch (Exception e) {
 			logger.error("ERRORE: ", e.getMessage());
 		}
    }
    
    @Test
    public void testBalanceInMemory() throws Exception {
    	try {
    		
	        ResponseEntity<String> response = service.getBalance(ACCOUNT_ID);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        tLogger.debug("Response della chiamata balance : " + jsonResponse.toString());
	        
    	 }catch (Exception e) {
    		tLogger.error("ERRORE: " + e.getMessage());
 		}
    }

    @Test
    public void testGetTransactions() throws Exception {
    	try {
	        String fromAccountingDate = "2019-01-01";
	        String toAccountingDate   = "2019-12-01";
	        		
	        ResponseEntity<String> response = service.getTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONObject payload = jsonResponse.getJSONObject("payload");
	
	        assertEquals(true, payload.has("list"));
	        logger.debug("Response della chiamata /transactions : \n {}", jsonResponse.toString());
        
	    }catch (Exception e) {
			logger.error("ERRORE: ", e.getMessage());
		}
    }
    
    @Test
    public void testGetTransactionsInMemory() throws Exception {
    	try {
	        String fromAccountingDate = "2019-01-01";
	        String toAccountingDate   = "2019-12-01";
	        		
	        ResponseEntity<String> response = service.getTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONObject payload = jsonResponse.getJSONObject("payload");
	
	        assertEquals(true, payload.has("list"));
	        tLogger.debug("Response della chiamata transactions : " + jsonResponse.toString());
        
	    }catch (Exception e) {
	    	tLogger.error("ERRORE: " + e.getMessage());
		}
    }

    @Test
    public void testMoneyTransfer() throws Exception {
    	try {
    		
	        Map<String, Object> request = new HashMap<>();
	        Map<String, Object> creditor = new HashMap<>();
	        Map<String, Object> account = new HashMap<>();
	
	        account.put("accountCode", "IT23A0336844430152923804660");
	        
	        creditor.put("name", "John Doe");
	        creditor.put("account", account);
	
	        request.put("creditor", creditor);
	        request.put("description", "Payment invoice 75/2025");
	        request.put("amount", "100");
	        request.put("currency", "EUR");
	        request.put("executionDate", "2025-03-26");
	
	        logger.debug("Request body: {}", request);
	
	        ResponseEntity<String> response = service.makeMoneyTransfer(ACCOUNT_ID, request);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	
	        logger.debug("Response della chiamata /moneyTransfer : \n {}", response.getBody());
	        
    	}catch (Exception e) {
    		logger.error("ERRORE: ", e.getMessage());
		}
    }
    
    @Test
    public void testMoneyTransferInMemory() throws Exception {
    	try {
    		
	        Map<String, Object> request = new HashMap<>();
	        Map<String, Object> creditor = new HashMap<>();
	        Map<String, Object> account = new HashMap<>();
	
	        account.put("accountCode", "IT23A0336844430152923804660");
	        
	        creditor.put("name", "John Doe");
	        creditor.put("account", account);
	
	        request.put("creditor", creditor);
	        request.put("description", "Payment invoice 75/2025");
	        request.put("amount", "100");
	        request.put("currency", "EUR");
	        request.put("executionDate", "2025-03-26");
	
	        tLogger.debug("Request body: " + request);
	
	        ResponseEntity<String> response = service.makeMoneyTransfer(ACCOUNT_ID, request);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	
	        tLogger.debug("Response della chiamata moneyTransfer : " + response.getBody());
	        
    	}catch (Exception e) {
    		tLogger.error("ERRORE: " + e.getMessage());
		}
    }
}
