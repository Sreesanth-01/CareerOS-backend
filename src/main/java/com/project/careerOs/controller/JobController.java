package com.project.careerOs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;
import com.project.careerOs.model.User;
import com.project.careerOs.service.JobServiceImpl;

@RestController
@RequestMapping("/api/job")
public class JobController {
    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService){
        this.jobService = jobService;
    }

    @PostMapping()
    public ResponseEntity<Object> addJobApplication(JobApplicationRequest jobApplicationRequest,@AuthenticationPrincipal User user){
        try{
            JobApplicationResponse response = jobService.addJobApplication(jobApplicationRequest, user.getEmail());
            return new ResponseEntity<>(response.getMessage(), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
