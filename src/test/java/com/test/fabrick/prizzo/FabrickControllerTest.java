package com.test.fabrick.prizzo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@WebMvcTest(FabrickController.class)
public class FabrickControllerTest {
	
	@Autowired
	private FabrickController controller;

	private static final Logger logger = LoggerFactory.getLogger(FabrickControllerTest.class);
    
    @Test
    public void testBalance() throws Exception {
    	
    	ResponseEntity<String> balance = controller.getBalance();
    	assertEquals(HttpStatus.OK, balance.getStatusCode());
    	
    	JSONObject jsonResponse = new JSONObject(balance.getBody());
    	logger.debug("Response della chiamata /balance : \n {}", jsonResponse.toString());
    }

    @Test
    public void testGetTransactions() throws Exception {
    	
    	String fromDate = "2019-01-01";
        String toDate = "2019-12-01";
        
    	ResponseEntity<String> transactions = controller.getTransactions(fromDate, toDate);
    	assertEquals(HttpStatus.OK, transactions.getStatusCode());

        JSONObject jsonResponse = new JSONObject(transactions.getBody());
        JSONObject payload = jsonResponse.getJSONObject("payload");
        
		assertEquals(true, payload.has("list"));
        
        logger.debug("Response della chiamata /transactions : \n {}", jsonResponse.toString());
    }
    
    @Test
    public void testMoneyTransfer() throws Exception 
    {
    	
    	 Map<String, Object> request = new HashMap<>();
    	 Map<String, Object> creditor = new HashMap<>();
    	 Map<String, Object> account = new HashMap<>();
    	 
    	 account.put("accountCode", "IT23A0336844430152923804660");
    	 
    	 creditor.put("name", "John Doe");
    	 creditor.put("account", account);
    	 
    	 request.put("creditor", creditor);
    	 
    	 request.put("creditor", creditor);
		 request.put("description", "Payment invoice 75/2025");
		 request.put("amount", "100");
		 request.put("currency", "EUR");
		 request.put("executionDate", "2025-03-26");  
    	 
    	 logger.debug(request.toString());
    	
         ResponseEntity<String> transfer = controller.makeMoneyTransfer(request);
         
         logger.debug("Response della chiamata /transactions : \n {}", transfer.getBody().toString());
    	
    }
    

}
