package com.copper.Nasir.Security;

import com.copper.Nasir.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                .claims(extraClaims)                  // era: .setClaims()   — @Deprecated 0.12
                .subject(user.getEmail())              // era: .setSubject()  — @Deprecated 0.12
                .issuedAt(new Date())                 // era: .setIssuedAt() — @Deprecated 0.12
                .expiration(new Date(System.currentTimeMillis() + expiration)) // era: .setExpiration() — @Deprecated 0.12
                .signWith(getSigningKey())             // era: .signWith(key, SignatureAlgorithm.HS256)
                //      SignatureAlgorithm enum inteiro é @Deprecated 0.12;
                //      o algoritmo agora é inferido a partir do tipo da chave
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String email = extractEmail(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()                          // era: Jwts.parserBuilder() — @Deprecated 0.12
                .verifyWith(getSigningKey())           // era: .setSigningKey()    — @Deprecated 0.12
                .build()
                .parseSignedClaims(token)             // era: .parseClaimsJws()   — @Deprecated 0.12
                .getPayload();                        // era: .getBody()           — @Deprecated 0.12
    }

    private SecretKey getSigningKey() {               // era: Key (java.security) — troca por SecretKey
        return Keys.hmacShaKeyFor(                    //   (javax.crypto), exigido pelo verifyWith()
                secret.getBytes(StandardCharsets.UTF_8) // charset explícito: evita comportamento
        );                                             // dependente da JVM
    }
}