package com.test.fabrick.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@SuppressWarnings("deprecation")
public class FabrickUrlBuilder {

    private static final String BASE_URL = "https://sandbox.platfr.io";
    private static final String BANKING_V4 = "/api/gbs/banking/v4.0/accounts/";

    public <T> String build(T accountId, String... paths) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path(BANKING_V4 + accountId);

        for (String path : paths) {
            uriBuilder.path("/" + path);
        }

        return uriBuilder.toUriString();
    }

    public <T> String buildWithQuery(T accountId, String[] paths, String[][] queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path(BANKING_V4 + accountId);

        for (String path : paths) {
            uriBuilder.path("/" + path);
        }

        for (String[] queryParam : queryParams) {
            uriBuilder.queryParam(queryParam[0], queryParam[1]);
        }

        return uriBuilder.toUriString();
    }
}
