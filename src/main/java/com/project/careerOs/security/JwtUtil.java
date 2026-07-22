package com.project.careerOs.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long expiration_time =  3600000*24;
    
    private String buildToken(String email){
        return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration_time))
                    .signWith(key)
                    .compact();
    }

    public String generateToken(UserDetails userDetails){
        return buildToken(userDetails.getUsername());
    }

    public String extractEmail(String token){
        return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
