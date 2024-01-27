package com.getlocals.getlocals.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "2cfb2c8018f5c77fc1353b7b049ecdb5e5161207866da436ce2060fd72f8e385";


    public String generateToken(
            UserPrincipal userDetails,
            Map<String, Object> extraClaims,
            Long duration
    ) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .addClaims(extraClaims)
                .setIssuer("GET_LOCALS")
                .signWith(getSigingKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public Boolean validateToken(
            String token,
            String username
    ) {
        final String email = extractEmail(token);
        return email.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigingKey() {
        byte[] secret_bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secret_bytes);
    }
}
