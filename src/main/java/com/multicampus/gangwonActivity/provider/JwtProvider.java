package com.multicampus.gangwonActivity.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${secret-key}")
    private String secretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // jwt 생성
    public String create(String userId) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .subject(userId)
                .issuedAt(new Date())
                .expiration(expiredDate)
                .compact();
    }

    // jwt 검증
    public String validate(String jwt) {
        try {
            SecretKey key = getSigningKey();
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            logger.warn("Expired JWT token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Malformed JWT token: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error validating JWT token: {}", e.getMessage());
        }
        return null;
    }
}
