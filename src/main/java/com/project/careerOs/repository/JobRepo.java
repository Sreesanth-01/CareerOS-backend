package com.project.careerOs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.careerOs.model.JobApplication;

@Repository
public interface JobRepo extends JpaRepository<JobApplication,Long> {
    
}
