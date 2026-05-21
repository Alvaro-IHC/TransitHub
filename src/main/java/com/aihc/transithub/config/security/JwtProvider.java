package com.aihc.transithub.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * JWT token provider for generating and validating JWT tokens.
 * Handles token creation, validation, and claim extraction.
 *
 * @author Alvaro Huanca
 */
@Component
public class JwtProvider {

    @Value("${security.jwt.secret:mySecretKeyForJWTGenerationWithAtLeast256BitsOfEntropyToBeSecure}")
    private String jwtSecret;

    @Value("${security.jwt.expiration:86400000}")
    private long jwtExpiration; // 24 hours default

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generate a JWT token for a user.
     *
     * @param userId  The user's unique identifier
     * @param username The user's username
     * @param role    The user's role (e.g., DRIVER, TICKET_AGENT, ADMIN)
     * @return JWT token string
     */
    public String generateToken(UUID userId, String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validate a JWT token.
     *
     * @param token JWT token string
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extract username from JWT token.
     *
     * @param token JWT token string
     * @return Username claim
     */
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extract user ID from JWT token.
     *
     * @param token JWT token string
     * @return User ID claim
     */
    public UUID getUserIdFromToken(String token) {
        Object userId = getClaims(token).get("userId");
        return UUID.fromString((String) userId);
    }

    /**
     * Extract role from JWT token.
     *
     * @param token JWT token string
     * @return Role claim
     */
    public String getRoleFromToken(String token) {
        return (String) getClaims(token).get("role");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}


