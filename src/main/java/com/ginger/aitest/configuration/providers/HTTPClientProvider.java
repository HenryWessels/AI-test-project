package com.ginger.aitest.configuration.providers;

import reactor.netty.http.client.HttpClient;

public class HTTPClientProvider {

    private HttpClient client;

    public HTTPClientProvider() {
        this.client = HttpClient.create();
    }

    public HttpClient getNewHttpClient() {
        this.client = HttpClient.create();
        configureHttpClient();
        return this.client;
    }

    public HttpClient getHttpClient() {
        configureHttpClient();
        return this.client;
    }

    public HttpClient getUnconfiguredClient() {
        return HttpClient.create();
    }

    private void configureHttpClient() {
        this.client.warmup().block();
        this.client.compress(true);
        this.client.followRedirect(true);
    }
}
