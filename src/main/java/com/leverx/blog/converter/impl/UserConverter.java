package com.leverx.blog.converter.impl;

import com.leverx.blog.converter.Convertible;
import com.leverx.blog.dto.UserDTO;
import com.leverx.blog.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Convertible<User, UserDTO> {
    @Override
    public User toEntity(UserDTO dto) {
        return new User(dto);
    }

    @Override
    public UserDTO toDTO(User entity) {
        return new UserDTO(entity);
    }
}
