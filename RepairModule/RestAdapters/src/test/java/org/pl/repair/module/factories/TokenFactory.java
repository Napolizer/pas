package org.pl.repair.module.factories;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.pl.repair.module.model.ClientAccessType;

import java.util.Date;

public class TokenFactory {

    public String generateUserToken(String username) {
        long now = System.currentTimeMillis();
        String secretKey = "SUPER_SECRET_KEY";
        long expirationTime = 900000L;
        return Jwts.builder()
                .setSubject(username)
                .claim("group", ClientAccessType.USERS)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime)) // 15 minutes expiration time
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public String generateAdminToken(String username) {
        long now = System.currentTimeMillis();
        String secretKey = "SUPER_SECRET_KEY";
        long expirationTime = 900000L;
        return Jwts.builder()
                .setSubject(username)
                .claim("group", ClientAccessType.ADMINISTRATORS)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime)) // 15 minutes expiration time
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }
}
