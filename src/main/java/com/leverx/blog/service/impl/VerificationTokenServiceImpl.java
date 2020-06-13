package com.leverx.blog.service.impl;

import com.leverx.blog.entity.VerificationToken;
import com.leverx.blog.service.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private static final int EXPIRY_TIME_IN_MINUTES = 60 * 24;

    @Override
    public VerificationToken createVerificationToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRY_TIME_IN_MINUTES);
        return new VerificationToken(UUID.randomUUID().toString(), calendar.getTime());
    }
}
