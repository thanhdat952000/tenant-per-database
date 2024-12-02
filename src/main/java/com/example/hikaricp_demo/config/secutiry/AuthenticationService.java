package com.example.hikaricp_demo.config.secutiry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

public class AuthenticationService {

    private static final long EXPIRATION_TIME = 4 * 60 * 60 * 1000; // 4 hours
    private static final String SECRET_KEY = "q3t6w9zCFJNcQfTjWnq3t6w9zCFJNcQfTjWnZr4u7xADGKaPd";
    private static final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final String PREFIX = "Bearer";

    public static void addToken(HttpServletResponse res, String username, String tenant) {
        String JwtToken = Jwts.builder()
                .setSubject(username)
                .setAudience(tenant)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY)
                .compact();
        System.out.println("jwt token = " + JwtToken);
        res.addHeader("Authorization", PREFIX + " " + JwtToken);
    }

    public static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader("Authorization");

        if (token != null && token.startsWith(PREFIX)) {
            String jwtToken = token.replace(PREFIX, "").trim();

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            String user = claims.getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }

        return null;
    }

    public static String getTenant(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (token == null || !token.startsWith(PREFIX)) {
            return null;
        }

        String jwtToken = token.replace(PREFIX, "").trim();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getAudience();
    }
}