package org.example.libraryrest.controller;

import org.example.libraryrest.service.MyUserDetailsService;
import org.example.libraryrest.webtoken.JwtService;
import org.example.libraryrest.webtoken.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PagesController  {

    @GetMapping("/login")
    public String login() {

        return "Login";
    }



}

