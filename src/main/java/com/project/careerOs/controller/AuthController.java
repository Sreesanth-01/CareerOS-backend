package com.project.careerOs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.LoginRequest;
import com.project.careerOs.dto.SignUpRequest;
import com.project.careerOs.model.User;
import com.project.careerOs.repository.UserRepo;
import com.project.careerOs.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest){
        userService.register(signUpRequest);

        return ResponseEntity.ok("SignUp successfull");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest loginRequest){
        String token = userService.login(loginRequest);

        Map<String,String> response = new HashMap<>();
        response.put("token",token);
        return ResponseEntity.ok(response); 


    }
}
