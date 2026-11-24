package com.project.careerOs.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.careerOs.dto.req.JobApplicationRequest;
import com.project.careerOs.dto.req.UpdateExpenseRequest;
import com.project.careerOs.dto.res.JobApplicationResponse;
import com.project.careerOs.model.JobApplication;
import com.project.careerOs.model.User;
import com.project.careerOs.repository.JobRepo;
import com.project.careerOs.repository.UserRepo;
import com.project.careerOs.service.JobServiceImpl;

public class JobServiceImplTest {
    @Mock 
    private JobRepo jobRepo;

    @Mock 
    private UserRepo userRepo;

    private JobServiceImpl jobService;

    @BeforeEach 
    void setup(){
        MockitoAnnotations.openMocks(this);

        jobService = new JobServiceImpl(jobRepo, userRepo);
    }

    @Test 
    void addJob_shouldSaveApplication(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        JobApplicationRequest request = new JobApplicationRequest();
        request.setCompanyName("Google");
        request.setJobRole("SDE");
        request.setSalary(1200000l);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        JobApplication saved = new JobApplication();

        when(jobRepo.save(any(JobApplication.class))).thenReturn(saved);

        JobApplicationResponse response = jobService.addJobApplication(request, email);

        assertEquals("Job application added successfully", response.getMessage());

        verify(jobRepo).save(any(JobApplication.class));
    }

    @Test 
    void addJob_shouldThrowExceptionWhenUserNotFound(){
        String email = "test2@gmail.com";

        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        JobApplicationRequest request = new JobApplicationRequest();

        RuntimeException exception = assertThrows(RuntimeException.class, ()->jobService.addJobApplication(request, email));

        assertEquals("User not found. Unable to add", exception.getMessage());

        verify(jobRepo,never()).save(any(JobApplication.class));
    }

    @Test 
    void getAllJobs_shouldReturnNonEmptyList(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        JobApplication j1 = new JobApplication();
        j1.setCompanyName("Google");
        JobApplication j2 = new JobApplication();
        j2.setCompanyName("Amazon");


        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(jobRepo.findByUser(any(User.class))).thenReturn(List.of(j1,j2));

        List<JobApplication> list = jobService.getAllJobApplications(email);

        assertEquals(2, list.size());
        assertEquals("Google", list.get(0).getCompanyName());

        verify(jobRepo).findByUser(user);
    }

    @Test 
    void getAllJobs_shouldThrowExceptionWhenUserNotFound(){
        String email = "test2@gmail.com";

        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, ()->jobService.getAllJobApplications(email));

        assertEquals("User not found. Unable to retreive", exception.getMessage());

        verify(jobRepo,never()).findByUser(any(User.class));
    }

    @Test 
    void getAllJobs_shouldThrowExceptionWhenListIsEmpty(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(jobRepo.findByUser(user)).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, ()->jobService.getAllJobApplications(email));
        assertEquals("No content", exception.getMessage());

    }

    @Test 
    void getJobById_shouldReturnJobById(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        JobApplication  job = new JobApplication();
        job.setCompanyName("Google");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(jobRepo.findByIdAndUser(1l, user)).thenReturn(job);

        JobApplication fetchedJob = jobService.getJobApplicationById(1l, email);

        assertEquals("Google", fetchedJob.getCompanyName());

        verify(jobRepo).findByIdAndUser(1l, user);
    }

    @Test 
    void getJobById_shouldThrowWhenNotFound(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(jobRepo.findByIdAndUser(1l, user)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->jobService.getJobApplicationById(0, email));

        assertEquals("Not found", exception.getMessage());
    }

    @Test 
    void deleteJob_shouldDeleteSuccessfully(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        JobApplication job = new JobApplication();

        when(jobRepo.findByIdAndUser(1l, user)).thenReturn(job);

        jobService.deleteApplication(email, 1l);

        verify(jobRepo).delete(job);
    }

    @Test
    void deleteJob_shouldThrowWhenUserNotFound(){
        String email = "temp2@gmail.com";

        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> jobService.deleteApplication(email, 1l));

        verify(jobRepo,never()).delete(any(JobApplication.class));

    }

    @Test 
    void deleteJob_shouldThrowWhenApplicationNotFound(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        when(jobRepo.findByIdAndUser(1l, user)).thenReturn(null);

        assertThrows(RuntimeException.class, ()->jobService.deleteApplication(email, 1l));

        verify(jobRepo,never()).delete(any(JobApplication.class));



    }

    @Test 
    void updateJob_shouldUpdateOnlyProvidedFields(){
        String email = "test2@gmail.com";

        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        JobApplication job = new JobApplication();
        job.setCompanyName("Google");
        job.setJobRole("SDE");

        UpdateExpenseRequest request = new UpdateExpenseRequest();
        request.setCompanyName("Amazon");

        when(jobRepo.findByIdAndUser(1l, user)).thenReturn(job);
        when(jobRepo.save(job)).thenReturn(job);

        JobApplication result = jobService.updateApplication(1l, email, request);

        assertEquals("Amazon", result.getCompanyName());
        assertEquals("SDE", result.getJobRole());

        verify(jobRepo).save(job);
    }


}
