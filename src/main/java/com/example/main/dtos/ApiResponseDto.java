package com.example.main.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponseDto<T> {
    private String status;
    private T response;

    public ApiResponseDto() {}

    public ApiResponseDto(String status, T response) {
        this.status = status;
        this.response = response;
    }
}