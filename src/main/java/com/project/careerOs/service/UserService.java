package com.project.careerOs.service;

import com.project.careerOs.dto.LoginRequest;
import com.project.careerOs.dto.SignUpRequest;

public interface UserService {
    void register(SignUpRequest signUpRequest);
    String login(LoginRequest loginRequest);
}
