package com.project.careerOs.service;

import com.project.careerOs.model.User;
import org.springframework.stereotype.Service;

import com.project.careerOs.dto.SignUpRequest;
import com.project.careerOs.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public void register(SignUpRequest signUpRequest){
        if(userRepo.existsByEmail(signUpRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setEmail(signUpRequest.getEmail());
        user.setMobile(signUpRequest.getMobile());
        user.setPassword(signUpRequest.getPassword());

        userRepo.save(user);

    }
}
