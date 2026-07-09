package com.project.careerOs.service;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;

public interface JobService {
    public JobApplicationResponse addJobApplication(JobApplicationRequest jobApplicationRequest, String email);
}
