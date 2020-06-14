package com.leverx.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${spring.security.jwt.expiration}")
    private long EXPIRATION;
    @Value("${spring.security.jwt.secret}")
    private String SECRET;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer_";
    private static final String EMPTY = "";

    @PostConstruct
    private void init() {
        SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
    }

    public String createJwt(String email) {
        Date now = new Date();
        return Jwts.builder()
                   .setClaims(Jwts.claims().setSubject(email))
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + EXPIRATION))
                   .signWith(SignatureAlgorithm.HS256, SECRET)
                   .compact();
    }

    public boolean tokenIsValid(String token) {
        return parseClaims(token).getBody().getExpiration().after(new Date());
    }

    public String getTokenFromRequestHeader(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTH_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return EMPTY;
    }

    public String getSubject(String token) {
        return parseClaims(token).getBody().getSubject();
    }
}
