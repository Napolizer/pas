package org.pl.gateway.module.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.pl.gateway.module.filters.CorsResponseFilter;
import org.pl.gateway.module.model.TokenClaims;
import org.pl.gateway.module.providers.TokenProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class ClientAuthenticationMechanism implements HttpAuthenticationMechanism {
    @Inject
    private TokenProvider tokenProvider;
    @Inject
    private CorsResponseFilter corsResponseFilter;
    private static final Logger LOGGER = Logger.getLogger(ClientAuthenticationMechanism.class.getName());
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        LOGGER.info("Started Authentication");
        if (!httpMessageContext.isProtected()) {
            LOGGER.info("Endpoint is not protected.");
            return httpMessageContext.doNothing();
        }

        String authorizationHeader = httpMessageContext.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length(), authorizationHeader.length());
            LOGGER.info("Retrieved token: " + token);
            try {
                Set<String> groups = new HashSet<>();
                TokenClaims tokenClaims = tokenProvider.getTokenClaims(token);
                LOGGER.info("Retrieved token claims: " + tokenClaims);
                groups.add(tokenClaims.getGroup());
                return httpMessageContext.notifyContainerAboutLogin(tokenClaims.getUsername(), groups);
            } catch (Exception e) {
                LOGGER.info("Authorization failed: " + e.getMessage());
                corsResponseFilter.addHeaders(response);
                return httpMessageContext.responseUnauthorized();
            }
        } else {
            LOGGER.info("Trying to access secured resource, with missing Authorization header, or in invalid format");
            corsResponseFilter.addHeaders(response);
            return httpMessageContext.responseUnauthorized();
        }
    }
}
