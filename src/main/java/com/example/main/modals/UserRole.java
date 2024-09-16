package com.example.main.modals;

public enum UserRole {
    USER,
    ADMIN;

    public String toUpperCase() {
        return name().toUpperCase();
    }
}
