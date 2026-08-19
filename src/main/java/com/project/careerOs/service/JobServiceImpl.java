package com.project.careerOs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.req.UpdateExpenseRequest;
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
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found. Unable to add"));

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
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found. Unable to retreive"));

        List<JobApplication> list = jobRepo.findByUser(user);
        if(list==null){
            throw new RuntimeException("No content");
        }
        return list;
        
    }

    @Override
    public JobApplication getJobApplicationById(long id, String email){
            User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found. Unable to retreive"));

            JobApplication job = jobRepo.findByIdAndUser(id, user);
            if(job==null){
                throw new RuntimeException("Not found");
            }
            return job;

    }

    @Override
    public void deleteApplication(String email, long id){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found. Unable to delete"));
        JobApplication application = jobRepo.findByIdAndUser(id,user);
        if(application==null){
            throw new RuntimeException("Application for the user not found");
        }
        jobRepo.delete(application);

    }

    @Override
    public JobApplication updateApplication(long id, String email, UpdateExpenseRequest request){
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found. Unable to update"));
        JobApplication application = jobRepo.findByIdAndUser(id, user);
        if(application==null){
            throw new RuntimeException("Application for the user not found");
        }
        if(request.getCompanyName()!=null){
            application.setCompanyName(request.getCompanyName());
        }
        if(request.getJobRole()!=null){
            application.setJobRole(request.getJobRole());
        }
        if(request.getSalary()!=0.0){
            application.setSalary(request.getSalary());
        }
        if(request.getStatus()!=null){
            application.setStatus(request.getStatus());
        }
        if(request.getAppliedDate()!=null){
            application.setAppliedDate(request.getAppliedDate());
        }
        if(request.getNotes()!=null){
            application.setNotes(request.getNotes());
        }

        JobApplication updatedApplication = jobRepo.save(application);
        return updatedApplication;
    }

}
