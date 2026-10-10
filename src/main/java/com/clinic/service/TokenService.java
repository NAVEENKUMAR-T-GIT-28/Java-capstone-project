package com.clinic.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final SecretKey signingKey;
    private final long expirationMs;
    public TokenService(@Value("${clinic.jwt.secret}") String secret,
                        @Value("${clinic.jwt.expiration-ms:3600000}") long expirationMs) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("clinic.jwt.secret must contain at least 32 UTF-8 bytes for HS256");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }
    public String generateToken(String email) {
        Date now = new Date();
        return Jwts.builder().subject(email).issuedAt(now).expiration(new Date(now.getTime() + expirationMs))
                .signWith(signingKey, Jwts.SIG.HS256).compact();
    }
    public SecretKey getSigningKey() { return signingKey; }
    public String extractSubject(String token) {
        Claims claims = Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
    public boolean isValid(String token) {
        try { return extractSubject(token) != null && !extractSubject(token).isBlank(); }
        catch (JwtException | IllegalArgumentException ex) { return false; }
    }
}
