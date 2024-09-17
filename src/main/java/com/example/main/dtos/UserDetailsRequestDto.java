package com.example.main.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsRequestDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Last name is mandatory")
    private String last_name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    //@NotBlank
    @NotNull
    private UserStatus accountStatus;

    @NotNull
    private UserRole role;

}
