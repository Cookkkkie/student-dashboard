package com.example.main.modals;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

     Long userID;
     String email;
     String password;

    public CustomUserDetails(Long userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
