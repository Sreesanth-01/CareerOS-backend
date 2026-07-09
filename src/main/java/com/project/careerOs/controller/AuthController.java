package com.project.careerOs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.req.LoginRequest;
import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.LoginResponse;
import com.project.careerOs.dto.res.SignUpResponse;
import com.project.careerOs.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest){
        try{
            SignUpResponse response = userService.register(signUpRequest);
            return ResponseEntity.ok(response.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse loginResponse = userService.login(loginRequest);

            Map<String,String> response = new HashMap<>();
            response.put("token",loginResponse.getToken());
            response.put("message",loginResponse.getMessage());
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }
}
