package com.example.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collection;

@Controller
public class MainController {

    @GetMapping("/")
    public String homePage(Principal principal, Authentication authentication) {

        if(principal == null) {
            return "/index";
        } else {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "redirect:/users";
            } else {
                return "redirect:/requests";
            }
        }
    }
}