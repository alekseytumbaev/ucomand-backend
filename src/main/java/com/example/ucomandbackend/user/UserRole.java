package com.example.ucomandbackend.user;

public enum UserRole {
    ROOT,
    ADMIN,
    USER;

    public String asScope() {
        return "SCOPE_" + name();
    }
}

