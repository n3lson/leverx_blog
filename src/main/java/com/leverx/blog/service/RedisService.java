package com.leverx.blog.service;

public interface RedisService {
    String put(Object value);
    Object get(Object key);
}
