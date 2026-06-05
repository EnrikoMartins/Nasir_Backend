package com.copper.Nasir.Security;

/* import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // CHAVE SECRETA: IMPORTANTE! Para produção, isso deve ir para as variáveis de ambiente.
    // Esta chave possui 256-bits, que é o mínimo exigido pelo algoritmo HS256.
    private static final String SECRET_KEY_STRING = "9a72614b65636e75616c646f5f6e61736972f7365637572655f6b65795f32303236";
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    }

    // 1. EXTRAIR O EMAIL DO USUÁRIO (SUBJECT) DO TOKEN
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. EXTRAIR UMA INFORMAÇÃO ESPECÍFICA (CLAIM) DO TOKEN
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 3. GERAR TOKEN APENAS COM OS DADOS PADRÃO DO USUÁRIO
    public String generateToken(String email) {
        return generateToken(new HashMap<>(), email);
    }

    // 4. GERAR TOKEN COM CLAIMS PERSONALIZADAS (CASO PRECISE ADICIONAR MAIS DADOS NO FUTURO)
    public String generateToken(Map<String, Object> extraClaims, String email) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Define a expiração para 24 horas (em milissegundos)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 5. VALIDAR SE O TOKEN PERTENCE AO USUÁRIO E NÃO EXPIROU
    public boolean isTokenValid(String token, String email) {
        final String username = extractUsername(token);
        return (username.equals(email)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // MÉTODO AUXILIAR PARA DECODIFICAR O JWT COMPLETO
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
*/
