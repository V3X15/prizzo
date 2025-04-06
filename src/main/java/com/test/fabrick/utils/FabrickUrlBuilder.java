package com.test.fabrick.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.fabrick.config.FabrickProperties;

@Component
@SuppressWarnings("deprecation")
public class FabrickUrlBuilder {

	private final FabrickProperties properties;
	
	public FabrickUrlBuilder(FabrickProperties properties) {
		this.properties = properties;
	}


    public <T> String build(T accountId, String... paths) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl())
                .path(properties.getBankingPath() + accountId);

        for (String path : paths) {
            uriBuilder.path("/" + path);
        }

        return uriBuilder.toUriString();
    }

    public <T> String buildWithQuery(T accountId, String[] paths, String[][] queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl())
                .path(properties.getBankingPath() + accountId);

        for (String path : paths) {
            uriBuilder.path("/" + path);
        }

        for (String[] queryParam : queryParams) {
            uriBuilder.queryParam(queryParam[0], queryParam[1]);
        }

        return uriBuilder.toUriString();
    }
}
