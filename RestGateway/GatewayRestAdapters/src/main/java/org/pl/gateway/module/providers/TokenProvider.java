package org.pl.gateway.module.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.TokenClaims;

import java.util.Date;

@ApplicationScoped
public class TokenProvider {
    @Inject
    @ConfigProperty(name="secret_key", defaultValue = "Default secret key")
    private String secretKey;
    @Inject
    @ConfigProperty(name="token_expiration_time", defaultValue = "900000")
    private Long expirationTime;

    public TokenClaims getTokenClaims(String token) throws ValidationException {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            String role = claims.get("group").toString();
            return new TokenClaims(username, role);
        } catch (RuntimeException e) {
            throw new ValidationException(e);
        }
    }
}
