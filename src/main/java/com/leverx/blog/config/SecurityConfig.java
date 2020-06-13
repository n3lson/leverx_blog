package com.leverx.blog.config;

import com.leverx.blog.security.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConfig config;
    private static final String AUTH_ENDPOINT = "/auth/**";

    public SecurityConfig(JwtConfig config) {
        this.config = config;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(AUTH_ENDPOINT).permitAll()
            .anyRequest().authenticated()
        .and()
            .apply(config);
    }
}
