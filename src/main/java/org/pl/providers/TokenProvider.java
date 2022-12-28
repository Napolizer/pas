package org.pl.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.pl.model.TokenClaims;

import javax.validation.ValidationException;
import java.util.Date;

public class TokenProvider {
    private final String SECRET_KEY = "SECRET_KEY";
    public String generateToken(String username, String password) {
        // TODO validate if user exist etc.
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject("SampleUser")
                .claim("username", username)
                .claim("group", "Users")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 10000000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public TokenClaims getTokenClaims(String token) throws ValidationException {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String username = claims.get("username").toString();
            String role = claims.get("group").toString();
            return new TokenClaims(username, role);
        } catch (RuntimeException e) {
            throw new ValidationException(e);
        }
    }
}
