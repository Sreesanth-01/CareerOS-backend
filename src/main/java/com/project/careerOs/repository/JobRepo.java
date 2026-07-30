package com.project.careerOs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.careerOs.model.JobApplication;
import com.project.careerOs.model.User;

@Repository
public interface JobRepo extends JpaRepository<JobApplication,Long> {

    List<JobApplication> findByUser(User user);

    JobApplication findByIdAndUser(long id, User user);
    
}
