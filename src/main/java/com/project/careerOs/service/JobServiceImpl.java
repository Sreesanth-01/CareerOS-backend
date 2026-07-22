package com.project.careerOs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;
import com.project.careerOs.model.JobApplication;
import com.project.careerOs.model.User;
import com.project.careerOs.repository.JobRepo;
import com.project.careerOs.repository.UserRepo;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepo jobRepo;
    private final UserRepo userRepo;

    public JobServiceImpl(JobRepo jobRepo,UserRepo userRepo){
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    @Override
    public JobApplicationResponse addJobApplication(JobApplicationRequest jobApplicationRequest, String email){
        System.out.println("Email: "+email);
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        JobApplication jobApplication = new JobApplication();

        jobApplication.setCompanyName(jobApplicationRequest.getCompanyName());
        jobApplication.setJobRole(jobApplicationRequest.getJobRole());
        jobApplication.setSalary(jobApplicationRequest.getSalary());
        jobApplication.setStatus(jobApplicationRequest.getStatus());
        jobApplication.setAppliedDate(jobApplicationRequest.getAppliedDate());
        jobApplication.setNotes(jobApplicationRequest.getNotes());

        jobApplication.setUser(user);

        jobRepo.save(jobApplication);

        return JobApplicationResponse.builder()
            .message("Job application added successfully")
            .build();

    }

    @Override
    public List<JobApplication> getAllJobApplications(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        List<JobApplication> list = jobRepo.findByUser(user);
        if(list==null){
            throw new RuntimeException("No content");
        }
        return list;
        
    }

}
