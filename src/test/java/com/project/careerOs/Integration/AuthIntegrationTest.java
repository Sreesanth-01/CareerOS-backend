package com.project.careerOs.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.classfile.ClassFile.Option;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.careerOs.dto.req.SignUpRequest;
import com.project.careerOs.dto.res.SignUpResponse;
import com.project.careerOs.repository.UserRepo;
import com.project.careerOs.service.UserService;

import lombok.var;

@SpringBootTest 
public class AuthIntegrationTest {
    @Autowired 
    private UserService service;

    @Autowired 
    private UserRepo repo;


    @Test 
    void register_shoudlSaveUserToDatabase(){
        SignUpRequest request = new SignUpRequest();
        request.setEmail("test@gmail.com");
        request.setMobile("9999988888");
        request.setPassword("password123");
        request.setUserName("test");

        SignUpResponse response = service.register(request);

        assertEquals("Registered Successfully", response.getMessage());

        var savedUser = repo.findByEmail("test@gmail.com");

        assertTrue(savedUser.isPresent());
        assertEquals("test@gmail.com", savedUser.get().getEmail());
        assertEquals("test", savedUser.get().getUserName());
        
    }
}
