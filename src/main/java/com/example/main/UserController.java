package com.example.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("user/")
public class UserController {
    @Autowired UserService service;
    @PostMapping("get/{id}")
    public ResponseEntity<User> getAccountById(@PathVariable int id, @RequestParam String password){
        User response = service.getByID(id);
       if(response != null) {
           return ResponseEntity.ok(response);
       }
        return ResponseEntity.notFound().build();
    }

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}

