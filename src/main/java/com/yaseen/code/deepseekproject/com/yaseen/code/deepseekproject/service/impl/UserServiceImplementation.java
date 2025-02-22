package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.impl;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.UserRepository;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.dto.User;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.UserService;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils.RandomStringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Slf4j
@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void registerUser(User user) {
        if(userRepository.existsByUsername(user.getUsername().toLowerCase())) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if(userRepository.existsByEmail(user.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public void processOAuthPostLogin(String email, String name) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            String randomPassword = RandomStringGenerator.getString();
            user = new User();
            user.setEmail(email.toLowerCase());
            user.setUsername(name.toLowerCase()); // Use Google name as username
            user.setPassword(passwordEncoder.encode(randomPassword)); // Dummy password
            log.info("Random Password: {}", randomPassword);

            userRepository.save(user);
            System.out.println("New Google user registered: " + email);
        } else {
            System.out.println("Existing Google user logged in: " + email);
        }
    }
}
