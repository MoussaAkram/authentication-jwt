package com.dcess.authentication.controller;

import com.dcess.authentication.model.AuthenticationRequest;
import com.dcess.authentication.model.AuthenticationResponse;
import com.dcess.authentication.model.RegisterRequest;
import com.dcess.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    // Registers a new user using the provided RegisterRequest object
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    // Authenticates a user based on the provided AuthenticationRequest object
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
