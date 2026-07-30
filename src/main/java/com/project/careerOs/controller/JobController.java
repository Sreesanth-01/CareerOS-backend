package com.project.careerOs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;
import com.project.careerOs.model.JobApplication;
import com.project.careerOs.service.JobServiceImpl;

@RestController
@RequestMapping("/api/job")
public class JobController {
    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService){
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Object> addJobApplication(@RequestBody JobApplicationRequest jobApplicationRequest,@AuthenticationPrincipal String email){
        try{
            JobApplicationResponse response = jobService.addJobApplication(jobApplicationRequest, email);
            return new ResponseEntity<>(response.getMessage(), HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<Object> getAllJobApplications(@AuthenticationPrincipal String email){
        try{
            List<JobApplication> list = jobService.getAllJobApplications(email);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApplicationByid(@PathVariable long id, @AuthenticationPrincipal String email){|
        try{
            jobService.deleteApplication(id,email);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}

