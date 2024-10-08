package com.example.main.controller;

import com.example.main.Exceptions.UserAlreadyExistsException;

import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.UserDetailsRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.main.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UserService userService;


    @GetMapping("/create")
    public ResponseEntity<ApiResponseDto<?>> createUser(@Valid @RequestBody UserDetailsRequestDto userDetailsRequestDto) throws UserAlreadyExistsException, UserServiceLogicException {
        return userService.createUser(userDetailsRequestDto);
    }

    @GetMapping("/giveAdmin/{email}")
    public ResponseEntity<ApiResponseDto<?>> giveAdminRules(@PathVariable String email) throws UserNotFoundException, UserServiceLogicException {
        return userService.createAdmin(email);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<ApiResponseDto<?>> getAccountByEmail(@PathVariable String email) throws UserNotFoundException, UserServiceLogicException {
        return userService.getUserByEmail(email);
    }
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponseDto<?>> getAllUsers() throws  UserServiceLogicException {
        return userService.getAllUsers();
    }

    @GetMapping("/update/{email}")
    public ResponseEntity<ApiResponseDto<?>> updateUser(@Valid @RequestBody UserDetailsRequestDto userDetailsRequestDto, @PathVariable String email) throws UserNotFoundException, UserServiceLogicException {
        return userService.updateUser(userDetailsRequestDto, email);
    }

    @GetMapping("/delete/{email}")
    public ResponseEntity<ApiResponseDto<?>> deleteUser(@PathVariable String email, HttpServletRequest request) throws UserNotFoundException, UserServiceLogicException {
        return userService.softDeleteUser(email);
    }


}