package com.example.main.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> {
    private String status;
    @Getter
    private T response;

}