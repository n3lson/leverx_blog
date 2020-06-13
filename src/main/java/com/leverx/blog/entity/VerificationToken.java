package com.leverx.blog.entity;

import java.io.Serializable;
import java.util.Date;

public class VerificationToken implements Serializable {
    private String hashCode;
    private Date expiryDate;

    public VerificationToken(String hashCode, Date expiryDate) {
        this.hashCode = hashCode;
        this.expiryDate = expiryDate;
    }

    public String getHashCode() {
        return hashCode;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
