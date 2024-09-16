package com.example.main.dtos;

import com.example.main.modals.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    private UserRole role;

    public UserRole getRole() {
        return UserRole.valueOf(role.toUpperCase());
    }

    private void setRole(UserRole role) {
        this.role = UserRole.valueOf(role.name());
    }

}
