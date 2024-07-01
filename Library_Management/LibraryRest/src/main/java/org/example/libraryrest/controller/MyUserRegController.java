package org.example.libraryrest.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.libraryrest.entity.MyUser;
import org.example.libraryrest.service.MyUserDetailsService;
import org.example.libraryrest.service.MyUserService;
import org.example.libraryrest.webtoken.JwtService;
import org.example.libraryrest.webtoken.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.View;

@RestController
@RequestMapping("/user/")
public class MyUserRegController {
    @Autowired
    private MyUserService myUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private View error;

    @PostMapping("register/")
    public MyUser register(
            @RequestBody MyUser u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return myUserService.saveUser(u);
    }
    @PostMapping("authenticate/")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        System.out.println(loginForm.getUsername());
        System.out.println(loginForm.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.getUsername(), loginForm.getPassword()
        ));

        if (authentication.isAuthenticated()) {
            System.out.println("Sent Token");
            return jwtService.generateToken(myUserDetailsService.loadUserByUsername(loginForm.getUsername()));
        }
        throw new UsernameNotFoundException("Invalid Credentials");
    }
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    @GetMapping(value = "mydetails/", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyUser getMyUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws Exception, UserNotFoundException {
        System.out.println("Received Token");
        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("Exception");
            throw new IllegalArgumentException("Invalid or missing Authorization header");
        }

        String jwtToken = token.substring(7);

        String email = jwtService.extractUsername(jwtToken);

        // Load user details from the database
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new UserNotFoundException("User not found");
        }

        // Retrieve user entity based on the username
        MyUser user = myUserService.getUserByEmail(userDetails.getUsername());
        if (user == null) {
            throw new UserNotFoundException("User details not found");
        }

        return user;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/number/")
    public ResponseEntity<Integer> getMyUserNumber() {
        Integer num=myUserService.numberOfStudents();
            return ResponseEntity.ok(num);

    }
@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @PutMapping("")
    public MyUser updatePassword(@RequestBody MyUser u) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        MyUser myUser = myUserService.getUserByEmail(email);
        myUser.setPassword(passwordEncoder.encode(u.getPassword()));
        return myUserService.saveUser(myUser);
    }
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/logout/")
    public void logout() {
            SecurityContextHolder.clearContext();

    }
}

