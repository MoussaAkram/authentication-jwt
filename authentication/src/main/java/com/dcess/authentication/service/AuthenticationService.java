package com.dcess.authentication.service;


import com.dcess.authentication.entity.User;
import com.dcess.authentication.model.AuthenticationRequest;
import com.dcess.authentication.model.AuthenticationResponse;
import com.dcess.authentication.model.RegisterRequest;
import com.dcess.authentication.repository.UserRepository;
import com.dcess.authentication.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class AuthenticationService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;
    private final TokenStorageService tokenService;

    @Autowired
    public AuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, TokenStorageService tokenService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    // Authenticates a user with provided credentials and returns an authentication response
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    // Registers a new user and returns an authentication response
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        tokenService.setAuthToken(jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }


}
