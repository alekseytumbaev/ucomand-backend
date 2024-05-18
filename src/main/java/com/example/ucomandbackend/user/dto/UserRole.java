package com.example.ucomandbackend.user.dto;

public enum UserRole {
    ROOT,
    ADMIN,
    USER;

    public String asScope() {
        return "SCOPE_" + name();
    }
}

