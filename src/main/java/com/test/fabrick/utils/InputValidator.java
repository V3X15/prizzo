package com.test.fabrick.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("unchecked")
public class InputValidator {

    public static <T> void validateAccountId(T accountId) {
        if (accountId == null || accountId.toString().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accountId non può essere nullo o vuoto");
        }

        String accountIdStr = accountId.toString();
        Pattern pattern = Pattern.compile("^[0-9]{16}$");
        Matcher matcher = pattern.matcher(accountIdStr);
        if (!matcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accountId deve essere un numero di 16 cifre");
        }
    }

    public static <T> void validateDate(T date) {
        if (date == null || date.toString().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La data non può essere nulla o vuota");
        }

        String dateStr = date.toString();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(dateStr);
        if (!matcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La data deve essere nel formato yyyy-MM-dd");
        }
    }

    public static <T> void validateMoneyTransferRequest(Map<String, T> request) {
        if (request == null || request.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il corpo della richiesta non può essere nullo o vuoto");
        }

        if (!request.containsKey("creditor") || !(request.get("creditor") instanceof Map)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'creditor' è obbligatorio e deve essere un oggetto");
        }

		Map<String, Object> creditor = (Map<String, Object>) request.get("creditor");

        if (!creditor.containsKey("name") || creditor.get("name") == null || ((String) creditor.get("name")).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'name' del creditore è obbligatorio e non può essere vuoto");
        }

        if (!creditor.containsKey("account") || !(creditor.get("account") instanceof Map)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'account' del creditore è obbligatorio e deve essere un oggetto");
        }

        Map<String, Object> account = (Map<String, Object>) creditor.get("account");
        if (!account.containsKey("accountCode") || account.get("accountCode") == null || ((String) account.get("accountCode")).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'accountCode' è obbligatorio nel campo 'account'");
        }

        if (!request.containsKey("amount") || !(request.get("amount") instanceof String) || ((String) request.get("amount")).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'amount' è obbligatorio e deve essere una stringa non vuota");
        }

        if (!request.containsKey("currency") || !(request.get("currency") instanceof String) || ((String) request.get("currency")).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'currency' è obbligatorio e deve essere una stringa non vuota");
        }

        if (!request.containsKey("executionDate") || !(request.get("executionDate") instanceof String) || ((String) request.get("executionDate")).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il campo 'executionDate' è obbligatorio e deve essere una stringa non vuota");
        }
    }

}
