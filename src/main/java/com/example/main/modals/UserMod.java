package com.example.main.modals;

import com.example.main.dtos.UserRole;
import com.example.main.dtos.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserMod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountStatus", nullable = false)
    private UserStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.USER;




    public UserMod() {}



    public UserMod(String name, String last_name, String email, String password, UserStatus accountStatus, UserRole role) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMod userMod = (UserMod) o;
        return userID == userMod.userID &&
                Objects.equals(name, userMod.name) &&
                Objects.equals(last_name, userMod.last_name) &&
                Objects.equals(email, userMod.email) &&
                Objects.equals(password, userMod.password) &&
                accountStatus == userMod.accountStatus &&
                role == userMod.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, name, last_name, email, password, accountStatus, role);
    }

}
