package com.tehzzcode.identity_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    public static final String SECRET_KEY = "0aca63699a5bac45ae9f1f51d1e8c0cb0111ebf0fccd57981028c72bd221f91a";


    public void validateToken(final String token) {
        Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.getBytes())
            .build()
            .parseClaimsJws(token);
    }


    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
            .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY.getBytes())
            .parseClaimsJws(token)
            .getBody();
    }
}
