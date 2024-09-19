package com.example.main.modals;

import com.example.main.dtos.UserRole;
import com.example.main.dtos.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

     Long userID;
     String email;
     String password;
     UserStatus accountStatus;
     UserRole role;

    public CustomUserDetails(Long userID, String email, String password, UserStatus accountStatus, UserRole role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userID.toString();
    }
    public long getID(){
        return userID;
    }
}
