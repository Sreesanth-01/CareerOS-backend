package com.project.careerOs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.SignUpRequest;
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
}
