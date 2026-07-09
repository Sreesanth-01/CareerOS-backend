package com.project.careerOs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.model.JobApplication;
import com.project.careerOs.service.JobServiceImpl;

@RestController
public class JobController {
    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService){
        this.jobService = jobService;
    }

    public ResponseEntity<JobApplication> addJobApplication(JobApplicationRequest jobApplicationRequest,String email){
        //code
    }
}
