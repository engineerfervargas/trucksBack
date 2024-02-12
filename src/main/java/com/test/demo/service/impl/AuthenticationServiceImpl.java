package com.test.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.demo.entity.User;
import com.test.demo.model.Role;
import com.test.demo.model.request.SignUpRequest;
import com.test.demo.model.request.SigninRequest;
import com.test.demo.model.response.JwtAuthenticationResponse;
import com.test.demo.repository.UserRepository;
import com.test.demo.service.AuthenticationService;
import com.test.demo.service.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private JwtService jwtService;
	@Autowired
    private AuthenticationManager authenticationManager;
	
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true)
                .enabled(true).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
    
}
