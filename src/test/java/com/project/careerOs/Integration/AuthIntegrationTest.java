package com.project.careerOs.Integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.careerOs.repository.UserRepo;
import com.project.careerOs.service.UserService;

@SpringBootTest 
public class AuthIntegrationTest {
    @Autowired 
    private UserService service;

    @Autowired 
    private UserRepo repo;

}
