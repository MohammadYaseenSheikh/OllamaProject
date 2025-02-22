package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.controller;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.dto.User;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("username", authentication.getName()); // 👈 Get logged-in username
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@ModelAttribute User user, HttpServletResponse response) throws IOException {
        userService.registerUser(user);

        response.sendRedirect("/login.html");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public Map<String, String> getUser(@AuthenticationPrincipal Object principal) {
        if (principal instanceof OAuth2User oAuth2User) {
            String name = oAuth2User.getAttribute("name"); // Google's full name
            String email = oAuth2User.getAttribute("email"); // Email fallback

            if (name == null || name.isEmpty()) {
                name = email; // If no name, use email instead
            }

            return Map.of("username", name);
        } else if (principal instanceof UserDetails userDetails) {
            return Map.of("username", userDetails.getUsername());
        }
        return Map.of("username", "Guest"); // Default case
    }


}
