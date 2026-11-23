package com.project.careerOs.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.careerOs.dto.req.LoginRequest;
import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.LoginResponse;
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

    @Test 
    void register_shouldThrowExceptionWhenEmailAlreadyExists(){
        SignUpRequest request = new SignUpRequest();
        request.setEmail("existing@gmail.com");
        request.setPassword("password123");

        when(userRepo.existsByEmail("existing@gmail.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.register(request));

        verify(userRepo,never()).save(any(User.class));
    }

    @Test 
    void login_shouldReturnTokenWhenLoginSuccessful(){
        LoginRequest request = new LoginRequest();
        request.setEmail("test1@gmail.com");
        request.setPassword("test1@123");

        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setPassword("encodedPassword");

        when(userRepo.findByEmail("test1@gmail.com")).thenReturn(Optional.of(user));

        when(passwordEncoder.matches("test1@123", "encodedPassword")).thenReturn(true);

        when(jwtUtil.generateToken(user)).thenReturn("mockToken");

        LoginResponse response = userService.login(request);

        assertEquals("Logged in successfully", response.getMessage());
        assertEquals("mockToken", response.getToken());

        verify(jwtUtil).generateToken(user);
    }
}
