package org.pl.repair.module.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pl.repair.module.model.TokenClaims;

@ApplicationScoped
public class TokenProvider {
    @Inject
    @ConfigProperty(name="secret_key", defaultValue = "Default secret key")
    private String secretKey;

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
