package com.example.main.controller;

import com.example.main.dtos.RegisterDTO;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import com.example.main.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String registerPage(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDTO", registerDTO);
        model.addAttribute("succes", false);
        return "register";

    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register"; // Return to registration page if there are validation errors
        }

        var bCryptEncoder = new BCryptPasswordEncoder();

        UserMod user = new UserMod();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
        user.setName(registerDTO.getName()); // Set name
        user.setLast_name(registerDTO.getLast_name()); // Set last name

        userRepository.save(user);
        model.addAttribute("registerDTO", new RegisterDTO());
        model.addAttribute("success", true);

        return "redirect:/auth/register";
    }



    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
