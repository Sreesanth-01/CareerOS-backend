package com.project.careerOs.service;

import com.project.careerOs.dto.req.LoginRequest;
import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.LoginResponse;
import com.project.careerOs.dto.res.SignUpResponse;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest loginRequest);
}
