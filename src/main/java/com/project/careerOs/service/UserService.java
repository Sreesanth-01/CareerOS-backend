package com.project.careerOs.service;

import com.project.careerOs.dto.req.LoginRequest;
import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.LoginResponse;

public interface UserService {
    void register(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest loginRequest);
}
