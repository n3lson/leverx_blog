package com.leverx.blog.entity;

public class ResetPassword {
    private VerificationToken token;
    private String newPassword;

    public ResetPassword(VerificationToken token, String newPassword) {
        this.token = token;
        this.newPassword = newPassword;
    }

    public VerificationToken getToken() {
        return token;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
