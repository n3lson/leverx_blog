package com.leverx.blog.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtFilter filter;

    public JwtConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
