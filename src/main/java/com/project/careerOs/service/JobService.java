package com.project.careerOs.service;

import java.util.List;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;
import com.project.careerOs.model.JobApplication;

public interface JobService {
    public JobApplicationResponse addJobApplication(JobApplicationRequest jobApplicationRequest, String email);
    public List<JobApplication> getAllJobApplications(String email);
    public void deleteApplication(String email,long id);
}
