package org.pl.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pl.model.Client;
import org.pl.model.TokenClaims;

import javax.validation.ValidationException;
import java.util.Date;

public class TokenProvider {
    @Inject
    @ConfigProperty(name="secret_key", defaultValue = "Default secret key")
    private String secretKey;
    @Inject
    @ConfigProperty(name="token_expiration_time", defaultValue = "900000")
    private Long expirationTime;

    public String generateToken(Client client) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(client.getUsername())
                .claim("group", client.getClientAccessType())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime)) // 15 minutes expiration time
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

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
