package com.leverx.blog.service.impl;

import com.leverx.blog.entity.VerificationToken;
import com.leverx.blog.service.RedisService;
import com.leverx.blog.service.VerificationTokenService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> template;
    private HashOperations<String, Object, Object> redis;
    private final VerificationTokenService verificationTokenService;

    private final static String KEY = "KEY";

    public RedisServiceImpl(RedisTemplate<String, Object> template, VerificationTokenService verificationTokenService) {
        this.template = template;
        this.verificationTokenService = verificationTokenService;
    }

    @PostConstruct
    private void init() {
        this.redis = template.opsForHash();
    }

    @Override
    public String put(Object value) {
        VerificationToken token = verificationTokenService.createVerificationToken();
        redis.put(KEY, token, value);
        redis.put(KEY, token.getHashCode(), token.getExpiryDate());
        return token.getHashCode();
    }

    @Override
    public Object get(Object key) {
        return redis.get(KEY, key);
    }
}
