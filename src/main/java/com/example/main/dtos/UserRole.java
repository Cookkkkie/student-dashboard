package com.example.main.dtos;

public enum UserRole {
    USER,
    ADMIN;

    public String toUpperCase() {
        return name().toUpperCase();
    }
}
