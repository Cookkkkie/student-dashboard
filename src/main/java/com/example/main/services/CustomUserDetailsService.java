package com.example.main.services;

import com.example.main.Exceptions.InactiveUserException;
import com.example.main.dtos.UserStatus;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserMod user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (user.getAccountStatus() == UserStatus.INACTIVE) {
            throw new InactiveUserException("Your account is inactive. Please contact support.");
        }

        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }
    }

