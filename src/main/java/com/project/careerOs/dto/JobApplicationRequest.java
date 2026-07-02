package com.project.careerOs.dto;

import java.time.LocalDate;

import com.project.careerOs.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequest {
    public String companyName;
    public String position;
    public long salary;
    public Status status;
    public LocalDate appliedDate;
    public String notes;
}
