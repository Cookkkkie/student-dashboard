package com.example.main.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String last_name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;


}
