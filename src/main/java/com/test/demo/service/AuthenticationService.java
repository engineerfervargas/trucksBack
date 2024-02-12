package com.test.demo.service;

import com.test.demo.model.request.SignUpRequest;
import com.test.demo.model.request.SigninRequest;
import com.test.demo.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    
	public JwtAuthenticationResponse signup(SignUpRequest request);

    public JwtAuthenticationResponse signin(SigninRequest request);
    
}
