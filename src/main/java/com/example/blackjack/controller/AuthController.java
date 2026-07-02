package com.example.blackjack.controller;

import com.example.blackjack.entity.User;
import com.example.blackjack.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ){
        logger.info("Register request received for username: {}", username);
        if (username.length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters");
            return "register";
        }
        if (!isValidPassword(password)) {
            model.addAttribute("error",
                    "Password must be at least 8 characters and include uppercase, lowercase, number, and special character");
            return "register";
        }
        if(userRepository.existsByUsername(username)){
            logger.info("Register failed: username already exists: {}", username);
            model.addAttribute("error","Username already exists");
            return "register";
        }
        String encodedPassword=passwordEncoder.encode(password);
        User user=new User(username,encodedPassword);
        User savedUser = userRepository.save(user);
        logger.info("User saved successfully. id={}, username={}", savedUser.getId(), savedUser.getUsername());
        return "redirect:/login?registered";
    }
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }
}
