package com.example.main;
import com.example.main.User;
import services.UserService;
import services.UserServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

//    @PostMapping("/create")
//    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
//        User createdUser = userService.createUser(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

    @DeleteMapping("/delete")
    public void deleteUser(@Valid @RequestBody int ID) {
        userService.deleteUser(ID);
    }
}
