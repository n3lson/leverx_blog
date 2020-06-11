package com.leverx.blog.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter implements Filter {
    private final JwtProvider jwtProvider;
    private final JwtAuthProvider auth;

    public JwtFilter(JwtProvider jwtProvider, JwtAuthProvider auth) {
        this.jwtProvider = jwtProvider;
        this.auth = auth;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.getTokenFromRequestHeader((HttpServletRequest) req);
        SecurityContext context = SecurityContextHolder.getContext();
        if (
//                context.getAuthentication() == null &&
                !token.isEmpty() && jwtProvider.tokenIsValid(token)) {
            context.setAuthentication(auth.getAuthentication(token));
        }
//        else {
//            context.setAuthentication(null);
//        }
        filterChain.doFilter(req, res);
    }
}
