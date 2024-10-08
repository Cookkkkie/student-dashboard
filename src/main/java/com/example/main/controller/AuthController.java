package com.example.main.controller;

import com.example.main.dtos.RegisterDTO;
import com.example.main.dtos.UserStatus;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;

@Controller
@RequestMapping("/")
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
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult bindingResult, Model model, HttpSession session) {

        model.addAttribute("incorrectPWD", false);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        var bCryptEncoder = new BCryptPasswordEncoder();
        if (Objects.equals(registerDTO.getPassword(),registerDTO.getPasswordConf())) {

            UserMod user = new UserMod();
            user.setEmail(registerDTO.getEmail());
            user.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            user.setName(registerDTO.getName());
            user.setLast_name(registerDTO.getLast_name());
            user.setAccountStatus(UserStatus.ACTIVE);

            userRepository.save(user);
            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);
            return "redirect:/login";

        }
        else{
            model.addAttribute("incorrectPWD", true);
            return "register";
        }
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
