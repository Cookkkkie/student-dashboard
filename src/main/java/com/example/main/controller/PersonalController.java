package com.example.main.controller;

import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import com.example.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/account")
public class PersonalController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String viewAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();

        Optional<UserMod> user = userRepository.findById(Long.valueOf(userID));


        if (user.isPresent()) {
            String role = String.valueOf(user.get().getRole());
            model.addAttribute("userID", userID);
            model.addAttribute("role", role);

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);

            return "personalaccount";
        }
        return "error";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();

        Optional<UserMod> user = userRepository.findById(Long.valueOf(userID));
        if (user.isPresent()) {
            UserMod currentUser = user.get();


            if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
                model.addAttribute("error", "Old password is incorrect");
                return "redirect:/account/";
            }


            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "New passwords do not match");
                return "redirect:/account/";
            }


            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);

            model.addAttribute("success", "Password changed successfully");
        } else {
            model.addAttribute("error", "User not found");
        }
        return "redirect:/main/dashboard";
    }


    @PostMapping("/delete-account")
    public String deleteAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();
        //System.out.println(userRepository.findByUserID(Long.valueOf(userID)).get().getEmail());
        try {
            userService.softDeleteUser(userRepository.findByUserID(Long.valueOf(userID)).get().getEmail());
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(null);
            return "redirect:/login";
        } catch (UserNotFoundException | UserServiceLogicException e) {
            model.addAttribute("error", "Error deleting account");
            return "personalaccount";
        }
    }
}
