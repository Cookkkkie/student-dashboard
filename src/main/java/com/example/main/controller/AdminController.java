package com.example.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adminpanel")
public class AdminController {


    @GetMapping("/")
    public String showUserManagementPage() {
        return "user_management";
    }


}


