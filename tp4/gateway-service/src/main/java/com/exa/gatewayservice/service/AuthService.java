package com.exa.gatewayservice.service;

import com.exa.gatewayservice.dto.LoginDTO;
import com.exa.gatewayservice.feignClient.UserFeignClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService extends OncePerRequestFilter {

    private final UserFeignClient userFeignClient;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Duration accessTokenValiditySeconds = Duration.ofSeconds(60);
    @Value("${jwt.secret}")
    private String secret;

    public AuthService(UserFeignClient userFeignClient, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userFeignClient = userFeignClient;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.username(),
                        loginDTO.password()
                );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .expiration(Date.from(Instant.now().plus(accessTokenValiditySeconds)))
                .compact();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Implement JWT validation of token and experity
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jwt.getBody();
            String username = claims.getSubject();
            Object authClaim = claims.get("auth");

            if (username == null || authClaim == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido: faltan reclamos");
                return;
            }

            String authString = authClaim.toString();
            if (authString.isBlank()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sin autoridades");
                return;
            }

            List<GrantedAuthority> authorities =
                    java.util.Arrays.stream(authString.split(","))
                            .map(s -> new SimpleGrantedAuthority(s.trim()))
                            .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
        } catch (JwtException | IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        }
    }
}
