package com.project.AI_Content_Repurposer.controller;


import com.project.AI_Content_Repurposer.dto.AuthRequest;
import com.project.AI_Content_Repurposer.dto.LoginRequest;
import com.project.AI_Content_Repurposer.dto.RegisterRequest;
import com.project.AI_Content_Repurposer.entity.User;
import com.project.AI_Content_Repurposer.repository.UserRepository;
import com.project.AI_Content_Repurposer.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticateController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody @Valid LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        ); // authintication of springsecurity not apache tomcat


        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token =  jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody  @Valid RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body("Email already exists");
        }
        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User();

        newUser.setUsername(request.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setEmail(request.getEmail());
        newUser.setRole("USER");


        userRepository.save(newUser);


        return ResponseEntity.ok("Registration successful");
    }
}

