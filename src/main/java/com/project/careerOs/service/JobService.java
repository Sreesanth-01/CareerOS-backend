package com.project.careerOs.service;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.model.JobApplication;

public interface JobService {
    public JobApplication addJobApplication(JobApplicationRequest jobApplicationRequest, String email);
}
