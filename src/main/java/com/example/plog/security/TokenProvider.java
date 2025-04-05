package com.example.plog.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.plog.config.jwt.JwtProperties;
import com.example.plog.repository.user.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {
    @Autowired
    private JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }
   
    public String createToken(final UserEntity userEntity){
        Date expiryDate = Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
        log.info("createToken: {}", userEntity.getId());
        return Jwts.builder()
                .signWith(getSigningKey(),SignatureAlgorithm.HS512)
                .setSubject(String.valueOf(userEntity.getId()))
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }
    
    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
                
        return claims.getSubject();
    }
}
