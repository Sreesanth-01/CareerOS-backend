package com.project.careerOs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.careerOs.dto.req.LoginRequest;
import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.LoginResponse;
import com.project.careerOs.dto.res.SignUpResponse;
import com.project.careerOs.service.UserService;

public class AuthControllerTest {
    
    @Mock 
    private UserService userService;

    private AuthController controller;

    @BeforeEach 
    void setup(){
        MockitoAnnotations.openMocks(this);

        controller = new AuthController(userService);
    }

    @Test 
    void signUp_shouldReturn200WhenRegistrationSuccessfull(){
        SignUpRequest request = new SignUpRequest();
        request.setEmail("test4@gmail.com");

        SignUpResponse response = SignUpResponse.builder()
                                    .message("Registered Successfully")
                                    .build();

        when(userService.register(request)).thenReturn(response);

        ResponseEntity<Object> result = controller.signUp(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Registered Successfully", result.getBody());

        verify(userService).register(request);
    }

    @Test 
    void signUp_shoudlReturn500WhenRegistrationFails(){
        SignUpRequest request = new SignUpRequest();
        request.setEmail("test4@gmail.com");

        when(userService.register(request)).thenThrow(new RuntimeException("Email already exists"));

        ResponseEntity<Object> result = controller.signUp(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Email already exists", result.getBody());


    }

    @Test 
    void login_shoudlReturn200AndTokenWhenLoginSucceeds(){
        LoginRequest request = new LoginRequest();
        request.setEmail("test4@gmail.com");

        LoginResponse response = LoginResponse.builder()
                                    .userName("test4")
                                    .token("myToken")
                                    .build();
        
        when(userService.login(request)).thenReturn(response);

        ResponseEntity<Object> result = controller.login(request);
        Map<String,String> map =(Map<String,String>) result.getBody();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("myToken", map.get("token"));
        assertEquals("test4", map.get("userName"));

        verify(userService).login(request);
    }

    @Test 
    void login_shouldReturn500WhenFailed(){
        LoginRequest request = new LoginRequest();

        when(userService.login(request)).thenThrow(new RuntimeException("Login Failed"));

        ResponseEntity<Object> result = controller.login(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Login Failed", result.getBody());
    }
}
