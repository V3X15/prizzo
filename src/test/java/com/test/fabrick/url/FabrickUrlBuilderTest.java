package com.test.fabrick.url;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.test.fabrick.utils.FabrickUrlBuilder;

public class FabrickUrlBuilderTest {

    private final FabrickUrlBuilder urlBuilder = new FabrickUrlBuilder();

    @Test
    void testBuildUrlWithSinglePath() {
        String accountId = "123456";
        String url = urlBuilder.build(accountId, "balance");

        assertThat(url).isEqualTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123456/balance");
    }

    @Test
    void testBuildUrlWithMultiplePaths() {
        String accountId = "123456";
        String url = urlBuilder.build(accountId, "payments", "money-transfers");

        assertThat(url).isEqualTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123456/payments/money-transfers");
    }

    @Test
    void testBuildUrlWithQueryParams() {
        String accountId = "123456";
        String[][] queryParams = {
            {"fromAccountingDate", "2024-01-01"},
            {"toAccountingDate", "2024-01-31"}
        };
        String url = urlBuilder.buildWithQuery(accountId, new String[] { "transactions" }, queryParams);

        assertThat(url).isEqualTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123456/transactions?fromAccountingDate=2024-01-01&toAccountingDate=2024-01-31");
    }

    @Test
    void testBuildUrlWithNoPath() {
        String accountId = "123456";
        String url = urlBuilder.build(accountId);

        assertThat(url).isEqualTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123456");
    }

    @Test
    void testBuildWithQueryParamsNoPaths() {
        String accountId = "123456";
        String[][] queryParams = {{"fromAccountingDate", "2024-01-01"}};
        String url = urlBuilder.buildWithQuery(accountId, new String[] {}, queryParams);

        assertThat(url).isEqualTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123456?fromAccountingDate=2024-01-01");
    }
}
