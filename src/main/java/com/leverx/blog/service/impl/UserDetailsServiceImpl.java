package com.leverx.blog.service.impl;

import com.leverx.blog.entity.User;
import com.leverx.blog.security.JwtUser;
import com.leverx.blog.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }
        return new JwtUser(user.get().getPassword(), user.get().getEmail());
    }
}
