package com.project.careerOs.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.SignUpResponse;
import com.project.careerOs.model.User;
import com.project.careerOs.repository.UserRepo;
import com.project.careerOs.security.JwtUtil;
import com.project.careerOs.service.UserServiceImpl;

public class UserServiceImplTest {
    @Mock 
    private UserRepo userRepo;

    @Mock 
    private PasswordEncoder passwordEncoder;

    @Mock 
    private JwtUtil jwtUtil;

    private UserServiceImpl userService;

    @BeforeEach 
    void setup(){
        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepo, passwordEncoder, jwtUtil);
    }

    @Test 
    void register_shouldRegisterUserSuccessfully(){

        SignUpRequest request = new SignUpRequest();
        request.setUserName("Test1");
        request.setEmail("test1@gmail.com");
        request.setMobile("9999999999");
        request.setPassword("test1@123");

        when(userRepo.existsByEmail("test1@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("test1@123")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setEmail("test1@gmail.com");
        savedUser.setPassword("encodedPassword");

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        SignUpResponse response = userService.register(request);

        assertEquals("Registered Successfully", response.getMessage());

        verify(userRepo).existsByEmail("test1@gmail.com");
        verify(passwordEncoder).encode("test1@123");
        verify(userRepo).save(any(User.class));
    }
}
