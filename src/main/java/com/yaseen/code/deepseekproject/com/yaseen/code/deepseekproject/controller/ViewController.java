package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    @GetMapping("/home")
    public String home() {
        return "home";  // Returns home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // Returns login.html
    }

    @GetMapping("/register")
    public String register() {
        return "home";  // Returns register.html
    }
}

