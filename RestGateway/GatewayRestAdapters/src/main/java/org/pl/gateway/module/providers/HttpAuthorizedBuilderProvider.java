package org.pl.gateway.module.providers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.io.Serializable;
import java.net.http.HttpRequest;

@Provider
@SessionScoped
public class HttpAuthorizedBuilderProvider implements Serializable {
    @Context
    private HttpHeaders httpHeaders;

    public HttpRequest.Builder builder() {
        return HttpRequest.newBuilder()
                .header("Authorization", httpHeaders.getHeaderString("Authorization"));
    }
}
