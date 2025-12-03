package com.exa.gatewayservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

//TODO completar
@Service
public class JwtService {
    private static final String SECRET = "8rjjf6ehq9rufj4h4ghg9geyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

    public String getToken(UserDetails user){
        Map<String, Object> extraClaims = new HashMap<>();

        List<String> authoritiesList = new ArrayList<>();

        for(GrantedAuthority authority: user.getAuthorities()){
            authoritiesList.add(authority.getAuthority());
        }

        StringBuilder authorities = new StringBuilder();

        for(int i=0; i<authoritiesList.size();i++){
            authorities.append(authoritiesList.get(i));
            if(i<authoritiesList.size()-1){
                authorities.append(", ");
            }
        }

        extraClaims.put("authorities", authoritiesList);

        return getToken(extraClaims, user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8)) //8 horas
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {return getExpiration(token).before(Date.from(Instant.now()));
    }

    private Date getExpiration(String token){
        return getClaim(token,Claims::getExpiration);
    }
}
