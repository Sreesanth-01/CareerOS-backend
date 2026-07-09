package com.project.careerOs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;
import com.project.careerOs.service.JobServiceImpl;

@RestController
public class JobController {
    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService){
        this.jobService = jobService;
    }

    public ResponseEntity<Object> addJobApplication(JobApplicationRequest jobApplicationRequest,String email){
        try{
            JobApplicationResponse response = jobService.addJobApplication(jobApplicationRequest, email);
            return new ResponseEntity<>(response.getMessage(), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
