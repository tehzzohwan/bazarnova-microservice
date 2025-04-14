package com.tehzzcode.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public static final String SECRET = "0aca63699a5bac45ae9f1f51d1e8c0cb0111ebf0fccd57981028c72bd221f91a";

    public void validateToken(final String token) {
        Jwts.parserBuilder()
            .setSigningKey(SECRET.getBytes())
            .build()
            .parseClaimsJws(token);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
