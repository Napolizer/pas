package org.pl.filters;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "*");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add(
                "Access-Control-Expose-Headers",
                "*");
    }

    public void addHeaders(HttpServletResponse response) {
        response.addHeader(
                "Access-Control-Allow-Origin", "*");
        response.addHeader(
                "Access-Control-Allow-Credentials", "true");
        response.addHeader(
                "Access-Control-Allow-Headers",
                "*");
        response.addHeader(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.addHeader(
                "Access-Control-Expose-Headers",
                "*");
    }
}
