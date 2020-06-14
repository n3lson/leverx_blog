package com.leverx.blog.service.impl;

import com.leverx.blog.converter.impl.UserConverter;
import com.leverx.blog.dto.UserDTO;
import com.leverx.blog.entity.User;
import com.leverx.blog.repo.UserRepository;
import com.leverx.blog.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final UserConverter converter;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder, UserConverter converter) {
        this.repo = repo;
        this.encoder = encoder;
        this.converter = converter;
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> user = repo.getUserByEmail(email);
        return user.map(converter::toDTO);
    }

    @Override
    public User save(UserDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(converter.toEntity(user));
    }
}
