package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.service.spec.IJWTService;
import com.daja.waa_server_lab.service.spec.IRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTServiceImpl implements IJWTService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private final IRedisService redisService;

    public JWTServiceImpl(IRedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        String token = generateToken(claims, userDetails);
        storeToken(token, Duration.ofMillis(jwtExpiration));
        return token;
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = buildToken(extraClaims, userDetails, jwtExpiration);
        storeToken(token, Duration.ofMillis(jwtExpiration));
        return token;
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        String refreshToken = buildToken(new HashMap<>(), userDetails, refreshExpiration);
        storeToken(refreshToken, Duration.ofMillis(refreshExpiration));
        return refreshToken;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(UUID.randomUUID().toString())  // Add jti claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenNotExpiredAndRevoked(token);
    }

    @Override
    public boolean isTokenNotExpiredAndRevoked(String token) {
        return !isTokenExpired(token) && !isTokenRevoked(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private boolean isTokenRevoked(String token) {
        return "revoked".equals(redisService.get(token));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private void storeToken(String token, Duration expiration) {
        redisService.add(token, "valid", expiration);
    }

    @Override
    public void revokeToken(String token) {
        redisService.add(token, "revoked", Duration.ofMillis(jwtExpiration));
    }
}
