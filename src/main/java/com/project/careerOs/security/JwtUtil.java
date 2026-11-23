package com.project.careerOs.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
    private final SecretKey key;

    private final long expiration_time =  3600000*24;

    public JwtUtil(@Value("${app.jwt.secret}") String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    private String buildToken(String email){
        return Jwts.builder()
                    .subject(email)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis()+expiration_time))
                    .signWith(key)
                    .compact();
    }

    public String generateToken(UserDetails userDetails){
        return buildToken(userDetails.getUsername());
    }

    public String extractEmail(String token){
        return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
