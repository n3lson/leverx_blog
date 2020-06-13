package com.leverx.blog.service;

import com.leverx.blog.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    User save(User user);
}
