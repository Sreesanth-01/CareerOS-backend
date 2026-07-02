package com.project.careerOs.service;

import org.springframework.stereotype.Service;

import com.project.careerOs.dto.JobApplicationRequest;
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
    public JobApplication addJobApplication(JobApplicationRequest jobApplicationRequest, String email){
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        JobApplication jobApplication = new JobApplication();

        jobApplication.setCompanyName(jobApplicationRequest.getCompanyName());
        jobApplication.setPosition(jobApplicationRequest.getPosition());
        jobApplication.setSalary(jobApplicationRequest.getSalary());
        jobApplication.setStatus(jobApplicationRequest.getStatus());
        jobApplication.setAppliedDate(jobApplicationRequest.getAppliedDate());
        jobApplication.setNotes(jobApplicationRequest.getNotes());

        jobApplication.setUser(user);

        return jobRepo.save(jobApplication);

    }

}
