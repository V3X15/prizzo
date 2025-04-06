package com.test.fabrick.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fabrick")
public class FabrickProperties {

    private String baseUrl;
    private String apiKey;
    private String bankingPath;
    private String accountId;  

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBankingPath() {
        return bankingPath;
    }
    public void setBankingPath(String bankingPath) {
        this.bankingPath = bankingPath;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
