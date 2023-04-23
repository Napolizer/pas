package org.pl.gateway.module.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.io.Serializable;
import java.net.ProxySelector;
import java.net.http.HttpClient;

@ApplicationScoped
public class HttpProducer implements Serializable {
    @Produces
    @ApplicationScoped
    public HttpClient getHttpClient() {
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build();
    }
}
