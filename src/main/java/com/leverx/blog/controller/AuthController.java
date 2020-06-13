package com.leverx.blog.controller;

import com.leverx.blog.entity.EmailTokenResponse;
import com.leverx.blog.entity.ResetPassword;
import com.leverx.blog.entity.User;
import com.leverx.blog.entity.VerificationToken;
import com.leverx.blog.security.JwtProvider;
import com.leverx.blog.service.EmailService;
import com.leverx.blog.service.RedisService;
import com.leverx.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager auth;
    private final JwtProvider jwt;
    private final EmailService emailService;
    private final UserService userService;
    private final RedisService redis;

    private static final String CONFIRMATION_EMAIL_SUBJECT = "Confirm your email";
    private static final String CONFIRMATION_PASSWORD_SUBJECT = "Confirm new password";
    private static final String CONFIRM_URL = "http://localhost:8080/auth/confirm/";
    private static final String CHECK_CODE_URL = "http://localhost:8080/auth/check_code/";

    public AuthController(
            AuthenticationManager auth,
            JwtProvider jwt,
            EmailService emailService,
            UserService userService,
            RedisService redis) {
        this.auth = auth;
        this.jwt = jwt;
        this.emailService = emailService;
        this.userService = userService;
        this.redis = redis;
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        String hashCode = redis.put(user);
        emailService.sendConfirmationEmail(user.getEmail(), CONFIRMATION_EMAIL_SUBJECT, CONFIRM_URL + hashCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm/{hash_code}")
    public ResponseEntity<String> confirm(@PathVariable("hash_code") String hashCode) {
        Date expiryDate = (Date) redis.get(hashCode);
        if (expiryDate.compareTo(new Date()) >= 0) {
            User user = (User) redis.get(new VerificationToken(hashCode, expiryDate));
            if (user != null) {
                userService.save(user);
                return ResponseEntity.ok("Confirmation succeeded.");
            }
        }
        return ResponseEntity.ok("Confirmation failed.");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        auth.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        return ResponseEntity.ok(new EmailTokenResponse(user.getEmail(), jwt.createJwt(user.getEmail())));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity onForgotPassword(@RequestBody String email) {
        String code = redis.put(email);
        emailService.sendConfirmationEmail(email, CONFIRMATION_PASSWORD_SUBJECT, CHECK_CODE_URL + code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@RequestBody ResetPassword reset) {
        String email = (String) redis.get(reset.getToken());
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            user.get().setPassword(reset.getNewPassword());
            userService.save(user.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/check_code/{hash_code}")
    public ResponseEntity checkCode(@PathVariable("hash_code") String hashCode) {
        Date expiryDate = (Date) redis.get(hashCode);
        if (expiryDate.compareTo(new Date()) >= 0) {
            return ResponseEntity.ok(new VerificationToken(hashCode, expiryDate));
        }
        return ResponseEntity.noContent().build();
    }
}
