package com.dcess.authentication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    // Extracts the username from the token
    public String extractUserName(String accessToken){
        return extractClaims(accessToken, Claims::getSubject);
    }

    // Generates a JWT token with default claims
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    // Generates a JWT token with additional claims
    public String generateToken(Map<String, Objects> extraClaims,
                                UserDetails userDetails){

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    // Validates the token by checking its username and expiration
    public boolean isTokenValid(String accessToken , UserDetails userDetails){
        final String username = extractUserName(accessToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(accessToken);
    }

    // Checks if the token is expired
    public boolean isTokenExpired(String accessToken){
        return extractExpiration(accessToken).before(new Date());
    }

    // Extracts the expiration date from the token
    private Date extractExpiration(String accessToken) {
        return extractClaims(accessToken,Claims::getExpiration);
    }

    // Extracts a specific claim using a custom claims resolver function
    public <T> T extractClaims(String accessToken, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(accessToken);
        return claimsResolver.apply(claims);

    }

    // Extracts all claims from the JWT token
    public Claims extractAllClaims(String accessToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    // Decodes the secret key and returns a signing key
    private Key getSignInKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}

