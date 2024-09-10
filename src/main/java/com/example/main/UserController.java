package com.example.main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

}

