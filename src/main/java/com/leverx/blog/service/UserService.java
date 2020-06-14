package com.leverx.blog.service;

import com.leverx.blog.dto.UserDTO;
import com.leverx.blog.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserByEmail(String email);
    User save(UserDTO user);
}
