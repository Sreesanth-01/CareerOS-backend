package com.project.careerOs.service;

import com.project.careerOs.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.careerOs.dto.req.LoginRequest;
import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.LoginResponse;
import com.project.careerOs.dto.res.SignUpResponse;
import com.project.careerOs.repository.UserRepo;
import com.project.careerOs.security.JwtUtil;
// also implement forgot password, reset- password, logout 

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest){
        if(userRepo.existsByEmail(signUpRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setEmail(signUpRequest.getEmail());
        user.setMobile(signUpRequest.getMobile());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepo.save(user);
        return SignUpResponse.builder()
            .message("Registered Successfully")
            .build();

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(),user.getPassword());

        if(passwordMatches){
            String token = jwtUtil.generateToken(email);
            return LoginResponse.builder()
            .token(token)
            .message("Logged in successfully")
            .build();
        }
        else{
            throw new RuntimeException("Password mismatch");
        }

    }
}
